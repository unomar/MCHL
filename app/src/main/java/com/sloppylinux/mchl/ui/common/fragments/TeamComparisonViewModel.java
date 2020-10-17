package com.sloppylinux.mchl.ui.common.fragments;

import android.app.Application;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.sloppylinux.mchl.domain.Team;
import com.sloppylinux.mchl.domain.sportspress.LeagueTable;
import com.sloppylinux.mchl.util.MCHLWebservice;
import com.sloppylinux.mchl.util.WebserviceException;

/**
 * The view model for the settings page.  Mainly used for player lookup and selection.
 */
public class TeamComparisonViewModel extends AndroidViewModel
{

    private MutableLiveData<LeagueTable> teamTableData;

    private MCHLWebservice mchlWebservice = MCHLWebservice.getSingleton();

    public TeamComparisonViewModel(@NonNull Application application)
    {
        super(application);
        teamTableData = new MutableLiveData<>();
    }

    /**
     * Get and/or generate the list of players
     *
     * @param team The name to search for
     * @return A list of matching players
     */
    public LiveData<LeagueTable> getTeamTable(Team team)
    {
        new RetrieveLeagueTableTask().execute(team);

        return teamTableData;
    }

    /**
     * Task to retrieve player info asynchronously.
     */
    private class RetrieveLeagueTableTask extends AsyncTask<Team, LeagueTable, Void>
    {
        /**
         * Perform retrieve player info asynchronously
         *
         * @param teams the team to search for
         * @return null
         */
        @Override
        protected Void doInBackground(Team... teams)
        {
            LeagueTable leagueTable = null;
            try
            {
                Team team = teams[0];
                leagueTable = mchlWebservice.getStandings(team.getCurrentSeason(), team.getCurrentLeague());
                teamTableData.postValue(leagueTable);
            } catch (WebserviceException e)
            {
                teamTableData.postValue(null);
            }

            return null;
        }
    }
}
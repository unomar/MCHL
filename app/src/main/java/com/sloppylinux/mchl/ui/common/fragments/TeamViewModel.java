package com.sloppylinux.mchl.ui.common.fragments;

import android.app.Application;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.sloppylinux.mchl.domain.Team;
import com.sloppylinux.mchl.util.MCHLWebservice;
import com.sloppylinux.mchl.util.WebserviceException;

/**
 * The view model for the settings page.  Mainly used for player lookup and selection.
 */
public class TeamViewModel extends AndroidViewModel
{

    private final MCHLWebservice mchlWebservice = MCHLWebservice.getSingleton();
    private final MutableLiveData<Team> teamData;

    public TeamViewModel(@NonNull Application application)
    {
        super(application);
        teamData = new MutableLiveData<>();
    }

    /**
     * Get and/or generate the Team object
     *
     * @param teamId The team ID to search for
     * @return The hydrated team
     */
    public LiveData<Team> getTeam(long teamId)
    {
        new RetrieveTeamTask().execute(teamId);

        return teamData;
    }

    /**
     * Task to retrieve player info asynchronously.
     */
    private class RetrieveTeamTask extends AsyncTask<Long, Team, Void>
    {
        /**
         * Perform retrieve player info asynchronously
         *
         * @param teamIds the team to search for
         * @return null
         */
        @Override
        protected Void doInBackground(Long... teamIds)
        {
            Team team = null;
            try
            {
                team = mchlWebservice.getTeam(teamIds[0]);
                teamData.postValue(team);
            } catch (WebserviceException e)
            {
                teamData.postValue(null);
            }

            return null;
        }
    }
}
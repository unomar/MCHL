package com.sloppylinux.mchl.ui.results;

import android.app.Application;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.sloppylinux.mchl.domain.Player;
import com.sloppylinux.mchl.domain.TeamSchedule;
import com.sloppylinux.mchl.util.MCHLWebservice;
import com.sloppylinux.mchl.util.WebserviceException;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * The view model for the schedule page. Used to update player schedule info.
 */
public class RefreshResultsViewModel extends AndroidViewModel
{
    private final Logger LOG = Logger.getLogger(RefreshResultsViewModel.class.getName());

    private final MutableLiveData<List<TeamSchedule>> playerResults;

    private final MCHLWebservice mchlWebservice = MCHLWebservice.getSingleton();

    public RefreshResultsViewModel(@NonNull Application application)
    {
        super(application);
        playerResults = new MutableLiveData<>();
    }

    public LiveData<List<TeamSchedule>> getPlayerSchedule(Player player)
    {
        new UpdateResultsTask().execute(player);

        return playerResults;
    }

    /**
     * Task to retrieve player info asynchronously.
     */
    private class UpdateResultsTask extends AsyncTask<Player, List<TeamSchedule>, Void>
    {
        /**
         * Retrieve and persist player info
         *
         * @param players The players to query and persist
         * @return null
         */
        @Override
        protected Void doInBackground(Player... players)
        {
            List<TeamSchedule> results = new ArrayList<>();
            if (players.length == 1)
            {
                Player player = players[0];
                try
                {

                    for (Long teamId : player.getCurrentTeams())
                    {
                        results.add(mchlWebservice.getResults(teamId));
                    }
                    playerResults.postValue(results);
                } catch (WebserviceException e)
                {
                    LOG.warning(e.getMessage());
                }

            }
            return null;
        }
    }
}
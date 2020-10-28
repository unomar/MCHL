package com.sloppylinux.mchl.ui.schedule;

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
public class RefreshScheduleViewModel extends AndroidViewModel
{
    private final Logger LOG = Logger.getLogger(RefreshScheduleViewModel.class.getName());

    private MutableLiveData<List<TeamSchedule>> playerSchedule;

    private MCHLWebservice mchlWebservice = MCHLWebservice.getSingleton();

    public RefreshScheduleViewModel(@NonNull Application application)
    {
        super(application);
        playerSchedule = new MutableLiveData<>();
    }

    public LiveData<List<TeamSchedule>> getPlayerSchedule(Player player)
    {
        new UpdateScheduleTask().execute(player);

        return playerSchedule;
    }

    /**
     * Task to retrieve player info asynchronously.
     */
    private class UpdateScheduleTask extends AsyncTask<Player, List<TeamSchedule>, Void>
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
            List<TeamSchedule> schedule = new ArrayList<>();
            if (players.length == 1)
            {
                Player player = players[0];
                try
                {

                    for (Long teamId : player.getCurrentTeams())
                    {
                        schedule.add(mchlWebservice.getSchedule(teamId));
                    }
                    playerSchedule.postValue(schedule);
                } catch (WebserviceException e)
                {
                    LOG.warning(e.getMessage());
                }

            }
            return null;
        }
    }
}
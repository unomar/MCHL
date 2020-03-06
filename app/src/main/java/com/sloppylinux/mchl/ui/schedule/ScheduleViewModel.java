package com.sloppylinux.mchl.ui.schedule;

import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.sloppylinux.mchl.domain.Game;
import com.sloppylinux.mchl.domain.Player;
import com.sloppylinux.mchl.domain.TeamSchedule;
import com.sloppylinux.mchl.util.MCHLWebservice;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ScheduleViewModel extends ViewModel {

    private MCHLWebservice mchlWebservice = MCHLWebservice.getSingleton();

    private MutableLiveData<List<Game>> gameListData;

    public ScheduleViewModel() {
        gameListData = new MutableLiveData<>();
    }

    /**
     * Get team schedule data
     * @param player The player to query schedule data for
     * @return The list of games for all current teams of the requested player
     */
    public LiveData<List<Game>> getTeamScheduleData(Player player)
    {
        List<Long> currentTeams = player.getCurrentTeams();
        new RetrieveScheduleTask().execute(currentTeams.toArray(new Long[currentTeams.size()]));
        return gameListData;
    }

    /**
     * Task to retrieve player info asynchronously.
     */
    private class RetrieveScheduleTask extends AsyncTask<Long, List<Game>, Void>
    {
        /**
         * Perform retrieve player info asynchronously
         * @param teamIds The team ids to check schedule data for
         * @return null
         */
        @Override
        protected Void doInBackground(Long... teamIds) {
            List<Game> allGames = new ArrayList<>();
            for (Long teamId : teamIds) {
                TeamSchedule teamSchedule = mchlWebservice.getSchedule(teamId, false);
                allGames.addAll(teamSchedule.getGames());
            }

            if (allGames != null)
            {
                gameListData.postValue(allGames);
            }
            return null;
        }
    }
}
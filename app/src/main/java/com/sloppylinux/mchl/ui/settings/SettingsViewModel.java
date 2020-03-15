package com.sloppylinux.mchl.ui.settings;

import android.app.Application;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.sloppylinux.mchl.domain.Player;
import com.sloppylinux.mchl.util.MCHLWebservice;

import java.util.List;

/**
 * The view model for the settings page.  Mainly used for player lookup and selection.
 */
public class SettingsViewModel extends AndroidViewModel {

    private MutableLiveData<List<Player>> playerListData;

    private MutableLiveData<String> playerRetrievelInfo;

    private MCHLWebservice mchlWebservice = MCHLWebservice.getSingleton();

    public SettingsViewModel(@NonNull Application application) {
        super(application);
        playerListData = new MutableLiveData<>();
        playerRetrievelInfo = new MutableLiveData<>();
    }

    /**
     * Get and/or generate the list of players
     * @param playerName The name to search for
     * @return A list of matching players
     */
    public LiveData<List<Player>> getText(String playerName)
    {
        new RetrievePlayerTask().execute(playerName);

        return playerListData;
    }

    public LiveData<String> getPlayerInfo(Player player)
    {
        new RetrievePlayerInfoTask().execute(player);

        return playerRetrievelInfo;
    }

    /**
     * Task to retrieve player info asynchronously.
     */
    private class RetrievePlayerTask extends AsyncTask<String, Player, Void>
    {
        /**
         * Perform retrieve player info asynchronously
         * @param players The players to search for
         * @return null
         */
        @Override
        protected Void doInBackground(String... players) {
            List<Player> playerList = mchlWebservice.playerLookup(players[0]);

            if (playerList != null)
            {
                playerListData.postValue(playerList);
            }
            return null;
        }
    }

    /**
     * Task to retrieve player info asynchronously.
     */
    private class RetrievePlayerInfoTask extends AsyncTask<Player, String, Void>
    {
        /**
         * Retrieve and persist player info
         * @param players The players to query and persist
         * @return null
         */
        @Override
        protected Void doInBackground(Player... players) {

            if (players.length != 1)
            {
                playerRetrievelInfo.postValue("Unable to process multiple players");
            }
            else
            {
                mchlWebservice.updateConfig(players[0], getApplication().getBaseContext());
                playerRetrievelInfo.postValue("Player info retrieved!");
            }
            return null;
        }
    }
}
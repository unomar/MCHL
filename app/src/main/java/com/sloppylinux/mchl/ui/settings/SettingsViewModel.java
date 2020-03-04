package com.sloppylinux.mchl.ui.settings;

import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.sloppylinux.mchl.domain.Player;
import com.sloppylinux.mchl.util.MCHLWebservice;

import java.util.List;

/**
 * The view model for the settings page.  Mainly used for player lookup and selection.
 */
public class SettingsViewModel extends ViewModel {

    private MutableLiveData<List<Player>> playerListData;

    private MCHLWebservice mchlWebservice = MCHLWebservice.getSingleton();

    public SettingsViewModel() {
        playerListData = new MutableLiveData<>();
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
}
package com.sloppylinux.mchl.ui.settings;

import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.sloppylinux.mchl.domain.Player;
import com.sloppylinux.mchl.util.MCHLWebservice;

import java.util.Collections;
import java.util.List;

public class SettingsViewModel extends ViewModel {

    private MutableLiveData<List<Player>> mText;

    private MCHLWebservice mchlWebservice = MCHLWebservice.getSingleton();

    public SettingsViewModel() {
        mText = new MutableLiveData<>();
    }

    public LiveData<List<Player>> getText(String playerName)
    {
        new RetrievePlayerTask().execute(playerName);

        return mText;
    }

    private class RetrievePlayerTask extends AsyncTask<String, Player, Void>
    {
        @Override
        protected Void doInBackground(String... players) {
            List<Player> playerList = mchlWebservice.playerLookup(players[0]);

            if (playerList != null)
            {
                mText.postValue(playerList);
            }
            else
            {
                // TODO trigger failure state
                //mText.postValue("Could not lookup player");
            }
            return null;
        }
    }
}
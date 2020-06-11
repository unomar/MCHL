package com.sloppylinux.mchl.ui.common.fragments;

import android.app.Application;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.sloppylinux.mchl.domain.Player;
import com.sloppylinux.mchl.util.MCHLWebservice;
import com.sloppylinux.mchl.util.WebserviceException;

/**
 * The view model for the settings page.  Mainly used for player lookup and selection.
 */
public class RefreshViewModel extends AndroidViewModel
{

    private MutableLiveData<String> playerRetrieveInfo;

    private MCHLWebservice mchlWebservice = MCHLWebservice.getSingleton();

    public RefreshViewModel(@NonNull Application application)
    {
        super(application);
        playerRetrieveInfo = new MutableLiveData<>();
    }

    public LiveData<String> getPlayerInfo(Player player)
    {
        new RetrievePlayerInfoTask().execute(player);

        return playerRetrieveInfo;
    }

    /**
     * Task to retrieve player info asynchronously.
     */
    private class RetrievePlayerInfoTask extends AsyncTask<Player, String, Void>
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

            if (players.length != 1)
            {
                playerRetrieveInfo.postValue("Unable to process multiple players");
            } else
            {
                try
                {
                    mchlWebservice.updateConfig(players[0], getApplication().getBaseContext());
                    playerRetrieveInfo.postValue("Player info retrieved!");
                } catch (WebserviceException e)
                {
                    playerRetrieveInfo.postValue(e.getUserMessage());
                }

            }
            return null;
        }
    }
}
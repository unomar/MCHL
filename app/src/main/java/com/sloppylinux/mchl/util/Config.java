package com.sloppylinux.mchl.util;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.gson.Gson;
import com.sloppylinux.mchl.domain.Player;
import com.sloppylinux.mchl.domain.Team;
import com.sloppylinux.mchl.domain.TeamSchedule;
import com.sloppylinux.mchl.domain.sportspress.LeagueTable;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class Config
{
    private static final String CONFIG_FILE = "mchl.properties";
    public static final String PLAYER_JSON = "PlayerJSON";
    final String tag = "MCHL Config";

    private SharedPreferences pref;
    private Properties props = new Properties();
    private boolean loaded;
    private boolean changed;
    private Context context;

    private Player player;

    public Config(Context context)
    {
        this.context = context;

        pref = context.getSharedPreferences(tag, 0); // 0 - for private mode

        loaded = loadValues();
        if (!loaded)
        {
            player = null;
        }
    }

    /**
     * Persist config values to disk.
     * @return  True if successful
     */
    public boolean storeValues()
    {
        Gson gson = new Gson();

        String playerJson = gson.toJson(player);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(PLAYER_JSON, playerJson);
        editor.apply();

        return true;
    }

    /**
     * Load config values from disk.
     * @return True if successful
     */
    public boolean loadValues()
    {
        Gson gson = new Gson();

        String playerJson = pref.getString(PLAYER_JSON, "");
        player = gson.fromJson(playerJson, Player.class);
        changed = true;
        return true;
    }

    public Config reload()
    {
        loadValues();
        return this;
    }

    public boolean isChanged()
    {
        return changed;
    }

    public void setChanged(boolean changed)
    {
        this.changed = changed;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) { this.player = player;}

    public boolean isLoaded()
    {
        return loaded;
    }
}

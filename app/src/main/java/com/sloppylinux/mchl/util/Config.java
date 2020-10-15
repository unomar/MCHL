package com.sloppylinux.mchl.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.sloppylinux.mchl.domain.Player;
import com.sloppylinux.mchl.domain.sportspress.LeagueTable;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class Config
{
    public static final String PLAYER_JSON = "PlayerJSON";
    private static final String TABLES_JSON = "TablesJSON";
    final String tag = "MCHL Config";

    private SharedPreferences pref;
    private Properties props = new Properties();
    private boolean loaded;
    private boolean changed;
    private Context context;

    private Player player;

    private List<LeagueTable> leagueTables;

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
        String tablesJson = gson.toJson(leagueTables);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(PLAYER_JSON, playerJson);
        editor.putString(TABLES_JSON, tablesJson);
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
        String tablesJson = pref.getString(TABLES_JSON, "");
        Type listType = new TypeToken<ArrayList<LeagueTable>>(){}.getType();
        leagueTables = gson.fromJson(tablesJson, listType);
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

    public List<LeagueTable> getLeagueTables() {
        return leagueTables;
    }

    public void setLeagueTables(List<LeagueTable> leagueTables) {
        this.leagueTables = leagueTables;
    }
}

package com.sloppylinux.mchl.util;

import android.content.Context;
import android.util.Log;

import com.sloppylinux.mchl.domain.Player;

import java.util.Properties;

public class Config
{
    private static final String PLAYER_NAME = "PLAYER_NAME";
    private static final String PLAYER_ID = "PLAYER_ID";

    private static final String CONFIG_FILE = "mchl.properties";
    final String tag = "Config";
    private Properties props = new Properties();
    private boolean loaded;
    private boolean changed;
    private Context context;

    private Player player;

    public Config(Context context)
    {
        this.context = context;
        if (!loadValues(context))
        {
            player = null;
        }
    }

    public boolean storeValues()
    {
        // Store info to the config file
        props.setProperty(PLAYER_NAME, player.getName(Player.DEFAULT_SEPARATOR));
        props.setProperty(PLAYER_ID, Long.toString(player.getPlayerId()));
        try
        {
            props.store(context.openFileOutput(CONFIG_FILE,
                    Context.MODE_PRIVATE), "MCHL Properties");
        }
        catch (Exception e)
        {
            Log.e(tag, "Failed to write config output: " +
                    e.getMessage());
            return false;
        }
        return true;
    }

    public boolean loadValues(Context context)
    {
        try
        {
            props.load(context.openFileInput(CONFIG_FILE));
            player = new Player(Long.valueOf(props.getProperty(PLAYER_ID)), props.getProperty(PLAYER_NAME));
            loaded = true;
        }
        catch (Exception e)
        {
            // Do something
            Log.d(tag, e.getMessage());
            return false;
        }
        changed = true;
        return true;
    }

    public Config reload(Context context)
    {
        loadValues(context);
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

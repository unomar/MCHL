package com.sloppylinux.mchl.util;

import java.util.Properties;

import android.content.Context;
import android.util.Log;

public class Config
{
	private static final String SEASON_KEY = "SEASON";
	private static final String TEAM_KEY = "TEAM";
	private static final String DIVISION_KEY = "DIVISION";

	private static final String CONFIG_FILE = "mchl.properties";

	private Properties props = new Properties();
	private boolean loaded;
	private boolean changed;
	private Context context;
	private String season;
	private String division;
	private String team;
	
	final String tag = "Config";

	public Config(Context context)
	{
		this.context = context;
        if (!loadValues(context))
        {
            season = "";
            division = "";
            team = "";
        }
	}

	public boolean storeValues()
	{
		// Store info to the config file
		props.setProperty(SEASON_KEY, season);
		props.setProperty(TEAM_KEY, team);
		props.setProperty(DIVISION_KEY, division);

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
			season = props.getProperty(SEASON_KEY);
			team = props.getProperty(TEAM_KEY);
			division = props.getProperty(DIVISION_KEY);
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

	public String getSeason()
	{
		return season;
	}

	public void setSeason(String season)
	{
		this.season = season;
		changed = true;
	}

	public String getDivision()
	{
		return division;
	}

	public void setDivision(String division)
	{
		changed = true;
		this.division = division;
	}

	public String getTeam()
	{
		return team;
	}

	public void setTeam(String team)
	{
		changed = true;
		this.team = team;
	}

	public boolean isLoaded()
	{
		return loaded;
	}
}

package com.sloppylinux.mchl.domain;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Game extends Expirable implements Comparable<Game>, Serializable
{
	private static final long serialVersionUID = 1L;
	private String home;
	private String away;
	private int homeScore = 0;
	private int awayScore = 0;
	private String location;
	private Date date;
	
	private static Map<String, String> locationNames = new HashMap<String, String>();
	
	static
	{
		locationNames.put("Motto McLean Ice Arena", "Motto");
	}
	
	private transient static final SimpleDateFormat dateFormat = new SimpleDateFormat("EEE d MMM yyyy h:mm a");
	
    private transient static final SimpleDateFormat shortDateFormat = new SimpleDateFormat("dd/MM/yy h:mm a");
	
	public Game(String home, String away, Date date, int homeScore, int awayScore, String location)
	{
		this.home = home;
		this.away = away;
		this.date = date;
		this.homeScore = homeScore;
		this.awayScore = awayScore;
		this.location = location;
	}

	/**
	 * @return the home
	 */
	public String getHome()
	{
		return home;
	}

	/**
	 * @param home the home to set
	 */
	public void setHome(String home)
	{
		this.home = home;
	}

	/**
	 * @return the away
	 */
	public String getAway()
	{
		return away;
	}

	/**
	 * @param away the away to set
	 */
	public void setAway(String away)
	{
		this.away = away;
	}

	/**
	 * @return the homeScore
	 */
	public int getHomeScore()
	{
		return homeScore;
	}

	/**
	 * @param homeScore the homeScore to set
	 */
	public void setHomeScore(int homeScore)
	{
		this.homeScore = homeScore;
	}

	/**
	 * @return the awayScore
	 */
	public int getAwayScore()
	{
		return awayScore;
	}

	/**
	 * @param awayScore the awayScore to set
	 */
	public void setAwayScore(int awayScore)
	{
		this.awayScore = awayScore;
	}

	/**
	 * @return the location
	 */
	public String getLocation()
	{
		return location;
	}

    /**
     * @return a shortened location string
     */
    public String getShortLocation()
    {
    	String loc = locationNames.get(this.location); 
    	return loc != null ? loc : this.location;
    }

	/**
	 * @param location the location to set
	 */
	public void setLocation(String location)
	{
		this.location = location;
	}

	/**
	 * @return the date
	 */
	public Date getDate()
	{
		return date;
	}
	
	public String getDateString()
	{
		return dateFormat.format(date);
	}

    public String getShortDateString()
    {
        return shortDateFormat.format(date);
    }

	/**
	 * @param date the date to set
	 */
	public void setDate(Date date)
	{
		this.date = date;
	}

	@Override
	public int compareTo(Game arg0)
	{
		return this.date.compareTo(arg0.date);
	}
	
	@Override
	public String toString()
	{
		return this.home + " vs " + this.away;
	}
}

package com.sloppylinux.mchl.domain;

import com.sloppylinux.mchl.util.Utils;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Game extends Expirable implements Comparable<Game>, Serializable
{
    private static final long serialVersionUID = 1L;
    private transient static final SimpleDateFormat dateFormat = new SimpleDateFormat("EEE d MMM yyyy h:mm a");
    private transient static final SimpleDateFormat shortDateFormat = new SimpleDateFormat("dd/MM/yy h:mm a");
    private static Map<String, String> locationNames = new HashMap<String, String>();

    static
    {
        locationNames.put("Motto McLean Ice Arena", "Motto");
    }

    private Team homeTeam;
    private Team awayTeam;
    private int homeScore = 0;
    private int awayScore = 0;
    private String location;
    private Date date;
    private Result result;

    public Game(Team homeTeam, Team awayTeam, Date date, int homeScore, int awayScore, String location)
    {
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
        this.date = date;
        this.homeScore = homeScore;
        this.awayScore = awayScore;
        this.location = location;
    }

    public Team getHomeTeam()
    {
        return homeTeam;
    }

    public Team getAwayTeam()
    {
        return awayTeam;
    }

    /**
     * @return the home
     */
    public String getHomeTeamName()
    {
        return homeTeam.getName();
    }


    public String getHomeFormatted(int maxChars)
    {
        return Utils.getFormatted(this.homeTeam.getName(), maxChars);
    }

    /**
     * @return the away
     */
    public String getAwayTeamName()
    {
        return awayTeam.getName();
    }

    public String getAwayFormatted(int maxChars)
    {
        return Utils.getFormatted(this.awayTeam.getName(), maxChars);
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
     * @param location the location to set
     */
    public void setLocation(String location)
    {
        this.location = location;
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
     * @return the date
     */
    public Date getDate()
    {
        return date;
    }

    /**
     * @param date the date to set
     */
    public void setDate(Date date)
    {
        this.date = date;
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
     * Checks if the game has occurred or is in the future.
     *
     * @return True if the game time is in the future
     */
    public boolean isInFuture()
    {
        boolean isFuture = true;
        if (this.date != null)
        {
            isFuture = this.date.after(new Date());
        }
        return isFuture;
    }

    @Override
    public int compareTo(Game arg0)
    {
        return this.date.compareTo(arg0.date);
    }

    @Override
    public String toString()
    {
        return this.homeTeam + " vs " + this.awayTeam;
    }

    public Result getResult()
    {
        return result;
    }

    public void setResult(Result result)
    {
        this.result = result;
    }

    public enum Result
    {
        Win("win"),
        Loss("loss"),
        Tie("tie");

        private String resultStr;

        Result(String resultStr)
        {
            this.resultStr = resultStr;
        }

        public static Result fromString(String resultStr)
        {
            for (Result result : Result.values())
            {
                if (result.resultStr.equals(resultStr))
                {
                    return result;
                }
            }
            return Result.Tie;
        }
    }
}

package com.sloppylinux.mchl.domain;

import com.google.gson.annotations.SerializedName;
import com.sloppylinux.mchl.domain.sportspress.ResponseBase;

import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.List;
import java.util.Map;


public class Player extends ResponseBase implements Comparable<Player>, Serializable
{
    private static final long serialVersionUID = 1L;
    Statistics currentStats;
    // Player info
    @SerializedName("slug")
    private String firstLast;
    @SerializedName("leagues")
    private List<Long> leagues;
    @SerializedName("seasons")
    private List<Long> seasons;
    @SerializedName("positions")
    private List<Long> positions;
    @SerializedName("number")
    private int number;
    @SerializedName("teams")
    private List<Long> teams;
    @SerializedName("current_teams")
    private List<Long> currentTeams;
    @SerializedName("statistics")
    private Map<Long, Statistics> stats;

    public Player()
    {
    }

    public String getFirstLast()
    {
        return firstLast;
    }

    public void setFirstLast(String firstLast)
    {
        this.firstLast = firstLast;
    }

    public List<Long> getLeagues()
    {
        return leagues;
    }

    public void setLeagues(List<Long> leagues)
    {
        this.leagues = leagues;
    }

    public List<Long> getSeasons()
    {
        return seasons;
    }

    public void setSeasons(List<Long> seasons)
    {
        this.seasons = seasons;
    }

    public List<Long> getPositions()
    {
        return positions;
    }

    public void setPositions(List<Long> positions)
    {
        this.positions = positions;
    }

    public int getNumber()
    {
        return number;
    }

    public void setNumber(int number)
    {
        this.number = number;
    }

    public List<Long> getTeams()
    {
        return teams;
    }

    public void setTeams(List<Long> teams)
    {
        this.teams = teams;
    }

    public List<Long> getCurrentTeams()
    {
        return currentTeams;
    }

    public void setCurrentTeams(List<Long> currentTeams)
    {
        this.currentTeams = currentTeams;
    }

    public Map<Long, Statistics> getStats()
    {
        return stats;
    }

    public void setStats(Map<Long, Statistics> stats)
    {
        this.stats = stats;
    }

    /**
     * Get a players name separating first & last name by the specified separator
     *
     * @param separator The separator to use
     * @return The players name
     */
    public String getName(String separator)
    {
        String name = "";
        if (StringUtils.isNotEmpty(this.firstLast))
        {
            String[] names = this.firstLast.split("-");
            if (names != null && names.length > 1)
            {
                String firstName = StringUtils.capitalize(names[0]);
                String lastName = StringUtils.capitalize(names[1]);

                name = firstName + separator + lastName;
            }

        }
        return name;
    }

    @Override
    public int compareTo(Player other)
    {
        // Reverse the sort order so we order from High to Low
        return this.currentStats.compareTo(other.currentStats);
    }


    @Override
    public String toString()
    {
        return getName(" ");
    }

    public String getShortInfo() { return getName(" ") + " #" + getNumber();}

    public int getGamesPlayed()
    {
        return 0;
    }

    public int getGoals()
    {
        return 0;
    }

    public int getAssists()
    {
        return 0;
    }

    public int getPoints()
    {
        return 0;
    }

    public int getPims()
    {
        return 0;
    }
}

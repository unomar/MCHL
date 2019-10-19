package com.sloppylinux.mchl.domain;

import com.google.gson.annotations.SerializedName;

import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.Date;
import java.util.List;


public class Player implements Comparable<Player>, Serializable
{
    private static final long serialVersionUID = 1L;
    // Player info
    @SerializedName("id")
    private long playerId;

    @SerializedName("modified")
    private Date lastModified;

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
    List<Statistics> stats;

    Statistics currentStats;

    public Player()
    {
    }

    public long getPlayerId() {
        return playerId;
    }

    public void setPlayerId(long playerId) {
        this.playerId = playerId;
    }

    public Date getLastModified() {
        return lastModified;
    }

    public void setLastModified(Date lastModified) {
        this.lastModified = lastModified;
    }

    public String getFirstLast() {
        return firstLast;
    }

    public void setFirstLast(String firstLast) {
        this.firstLast = firstLast;
    }

    public List<Long> getLeagues() {
        return leagues;
    }

    public void setLeagues(List<Long> leagues) {
        this.leagues = leagues;
    }

    public List<Long> getSeasons() {
        return seasons;
    }

    public void setSeasons(List<Long> seasons) {
        this.seasons = seasons;
    }

    public List<Long> getPositions() {
        return positions;
    }

    public void setPositions(List<Long> positions) {
        this.positions = positions;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public List<Long> getTeams() {
        return teams;
    }

    public void setTeams(List<Long> teams) {
        this.teams = teams;
    }

    public List<Long> getCurrentTeams() {
        return currentTeams;
    }

    public void setCurrentTeams(List<Long> currentTeams) {
        this.currentTeams = currentTeams;
    }

    public List<Statistics> getStats() {
        return stats;
    }

    public void setStats(List<Statistics> stats) {
        this.stats = stats;
    }

    /**
     * Get a players name separating first & last name by the specified separator
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
}

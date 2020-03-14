package com.sloppylinux.mchl.domain;

import com.google.gson.annotations.SerializedName;
import com.sloppylinux.mchl.domain.sportspress.LeagueTable;
import com.sloppylinux.mchl.domain.sportspress.ResponseBase;

import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;


public class Player extends ResponseBase implements Comparable<Player>, Serializable {
    private static final long serialVersionUID = 1L;
    public static final String DEFAULT_SEPARATOR = "-";
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

    List<Team> playerTeams = new ArrayList<>();
    List<TeamSchedule> playerSchedule = new ArrayList<>();
    List<TeamSchedule> playerResults = new ArrayList<>();
    List<LeagueTable> leagueTables = new ArrayList<>();

    public Player() {
    }

    public Player(Long playerId, String playerName) {
        this.setId(playerId);
        this.setFirstLast(playerName);
    }

    public long getPlayerId() {
        return getId();
    }

    public void setPlayerId(long playerId) {
        this.setId(playerId);
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

    public Map<Long, Statistics> getStats() {
        return stats;
    }

    public void setStats(Map<Long, Statistics> stats) {
        this.stats = stats;
    }

    public List<Team> getPlayerTeams() {
        return playerTeams;
    }

    public void setPlayerTeams(List<Team> playerTeams) {
        this.playerTeams = playerTeams;
    }

    public List<TeamSchedule> getPlayerSchedule() {
        return playerSchedule;
    }

    public void setPlayerSchedule(List<TeamSchedule> playerSchedule) {
        this.playerSchedule = playerSchedule;
    }

    public List<TeamSchedule> getPlayerResults() {
        return playerResults;
    }

    public void setPlayerResults(List<TeamSchedule> playerResults) {
        this.playerResults = playerResults;
    }

    public List<LeagueTable> getLeagueTables() {
        return leagueTables;
    }

    public void setLeagueTables(List<LeagueTable> leagueTables) {
        this.leagueTables = leagueTables;
    }

    public List<Game> getPlayerGameList() {
        List<Game> playerGames = new ArrayList<>();
        for (TeamSchedule teamSchedule : getPlayerSchedule()) {
            playerGames.addAll(teamSchedule.getGames());
        }
        Collections.sort(playerGames);
        return playerGames;
    }

    public List<Game> getPlayerResultList() {
        List<Game> playerResults = new ArrayList<>();
        for (TeamSchedule teamSchedule : getPlayerResults()) {
            playerResults.addAll(teamSchedule.getGames());
        }
        Collections.sort(playerResults);
        Collections.reverse(playerResults);
        return playerResults;
    }

    /**
     * Get a players name separating first & last name by the specified separator
     *
     * @param separator The separator to use
     * @return The players name
     */
    public String getName(String separator) {
        String name = "";
        if (StringUtils.isNotEmpty(this.firstLast)) {
            String[] names = this.firstLast.split("-");
            if (names != null && names.length > 1) {
                String firstName = StringUtils.capitalize(names[0]);
                String lastName = StringUtils.capitalize(names[1]);

                name = firstName + separator + lastName;
            }

        }
        return name;
    }

    @Override
    public int compareTo(Player other) {
        if (this.currentStats == null)
        {
            return this.getName().compareTo((other.getName()));
        }
        else {
            // Reverse the sort order so we order from High to Low
            return this.currentStats.compareTo(other.currentStats);
        }
    }


    @Override
    public String toString() {
        return getName(" ");
    }

    public String getShortInfo() {
        return getName(" ") + " #" + getNumber();
    }

    public int getGamesPlayed() {
        return 0;
    }

    public int getGoals() {
        return 0;
    }

    public int getAssists() {
        return 0;
    }

    public int getPoints() {
        return 0;
    }

    public int getPims() {
        return 0;
    }

    /**
     * Helper method to determine if the player info requires updating.
     * @return True if we should query remotely for updated data
     */
    public boolean requiresUpdate()
    {
        return this.isExpired() || this.playerTeams.isEmpty();
    }

    public void clear()
    {
        clearList(this.teams);
        clearList(this.playerResults);
        clearList(this.playerSchedule);
        clearList(this.leagueTables);
        clearList(this.playerResults);
        clearList(this.playerSchedule);
        clearList(this.playerTeams);
    }

    /**
     * Null-Safe clear operation
     * @param list The list to clear
     */
    private void clearList(List list)
    {
        if (list != null)
        {
            list.clear();
        }
    }
}

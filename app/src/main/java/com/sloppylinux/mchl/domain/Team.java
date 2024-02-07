package com.sloppylinux.mchl.domain;

import com.google.gson.annotations.SerializedName;
import com.sloppylinux.mchl.domain.sportspress.LeagueTable;
import com.sloppylinux.mchl.domain.sportspress.PlayerStatistic;
import com.sloppylinux.mchl.domain.sportspress.ResponseBase;
import com.sloppylinux.mchl.domain.sportspress.TeamStatistic;
import com.sloppylinux.mchl.domain.sportspress.TeamTable;
import com.sloppylinux.mchl.util.Utils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Team extends ResponseBase implements Comparable<Team>, Serializable
{
    private static final long serialVersionUID = 1L;

    // Team info
    private List<Player> players;
    private Goalie goalie;
    private ArrayList<Game> schedule;
    private String captain;
    private String alternate1;
    private String alternate2;

    @SerializedName("seasons")
    private List<Long> seasonIds = new ArrayList<>();
    @SerializedName("leagues")
    private List<Long> leagueIds = new ArrayList<>();
    @SerializedName("lists")
    private List<Long> listIds;

    private TeamTable teamTable;

    private TeamStatistic teamStatistic;

    private LeagueTable leagueTable;
    // Stats
    private int gamesPlayed;
    private int wins;
    private int losses;
    private int ties;
    private int goalsFor;
    private int goalsAgainst;
    private int points;
    private int rank = 0;

    public Team()
    {
        players = new ArrayList<Player>();
        schedule = new ArrayList<Game>();
        goalie = new Goalie();
    }

    public Team(String name, long seasonId, long leagueId)
    {
        players = new ArrayList<Player>();
        schedule = new ArrayList<Game>();
        goalie = new Goalie();
        this.setName(name);
        this.seasonIds.add(seasonId);
        this.leagueIds.add(leagueId);
    }

    public void addPlayer(Player player)
    {
        players.add(player);
    }

    /**
     * @return the goalie
     */
    public Goalie getGoalie()
    {
        return goalie;
    }

    /**
     * @param goalie the goalie to set
     */
    public void setGoalie(Goalie goalie)
    {
        this.goalie = goalie;
    }

    /**
     * @param game The game to be added to the schedule
     */
    public void addGame(Game game)
    {
        schedule.add(game);
    }

    /**
     * @return the schedule
     */
    public List<Game> getSchedule()
    {
        return schedule;
    }

    /**
     * @return the captain
     */
    public String getCaptain()
    {
        return captain;
    }

    /**
     * @param captain the captain to set
     */
    public void setCaptain(String captain)
    {
        this.captain = captain;
    }

    /**
     * @return the alternate1
     */
    public String getAlternate1()
    {
        return alternate1;
    }

    /**
     * @param alternate1 the alternate1 to set
     */
    public void setAlternate1(String alternate1)
    {
        this.alternate1 = alternate1;
    }

    /**
     * @return the alternate2
     */
    public String getAlternate2()
    {
        return alternate2;
    }

    /**
     * @param alternate2 the alternate2 to set
     */
    public void setAlternate2(String alternate2)
    {
        this.alternate2 = alternate2;
    }

    public List<Player> getTopPlayers()
    {
        Collections.sort(players); // Sort players based on stats
        return players.subList(0, 2); // Return top 3
    }

    /**
     * @return the players
     */
    public List<Player> getPlayers()
    {
        return players;
    }

    /**
     * @param players the players to set
     */
    public void setPlayers(List<Player> players)
    {
        this.players = players;
    }

    /**
     * @return the gamesPlayed
     */
    public int getGamesPlayed()
    {
        return gamesPlayed;
    }

    /**
     * @param gamesPlayed the gamesPlayed to set
     */
    public void setGamesPlayed(int gamesPlayed)
    {
        this.gamesPlayed = gamesPlayed;
    }

    /**
     * @return the wins
     */
    public int getWins()
    {
        return wins;
    }

    /**
     * @param wins the wins to set
     */
    public void setWins(int wins)
    {
        this.wins = wins;
    }

    /**
     * @return the losses
     */
    public int getLosses()
    {
        return losses;
    }

    /**
     * @param losses the losses to set
     */
    public void setLosses(int losses)
    {
        this.losses = losses;
    }

    /**
     * @return the ties
     */
    public int getTies()
    {
        return ties;
    }

    /**
     * @param ties the ties to set
     */
    public void setTies(int ties)
    {
        this.ties = ties;
    }

    /**
     * @return the goalsFor
     */
    public int getGoalsFor()
    {
        return goalsFor;
    }

    /**
     * @param goalsFor the goalsFor to set
     */
    public void setGoalsFor(int goalsFor)
    {
        this.goalsFor = goalsFor;
    }

    /**
     * @return the goalsAgainst
     */
    public int getGoalsAgainst()
    {
        return goalsAgainst;
    }

    /**
     * @param goalsAgainst the goalsAgainst to set
     */
    public void setGoalsAgainst(int goalsAgainst)
    {
        this.goalsAgainst = goalsAgainst;
    }

    /**
     * @return the points
     */
    public int getPoints()
    {
        return points;
    }

    /**
     * @param points the points to set
     */
    public void setPoints(int points)
    {
        this.points = points;
    }

    public int getRank()
    {
        return rank;
    }

    public void setRank(int rank)
    {
        this.rank = rank;
    }

    public long getListId()
    {
        if (this.listIds.size() > 0)
        {
            return this.listIds.get(0);
        }
        return 0;
    }

    public List<Long> getListIds()
    {
        return listIds;
    }

    public void setListIds(List<Long> listIds)
    {
        this.listIds = listIds;
    }

    public TeamTable getTeamTable()
    {
        return teamTable;
    }

    public void setTeamTable(TeamTable teamTable)
    {
        this.teamTable = teamTable;
        if (teamTable != null)
        {
            PlayerStatistic teamData = teamTable.getTableData().get(String.valueOf(this.getId()));
        }
    }

    public long getCurrentSeason()
    {
        long seasonId = 0l;
        if (this.seasonIds != null && this.seasonIds.size() >= 1)
        {
            seasonId = this.seasonIds.get(0);
        }
        return seasonId;
    }

    public long getCurrentLeague()
    {
        long leagueId = 0l;
        if (this.leagueIds != null && this.leagueIds.size() >= 1)
        {
            leagueId = this.leagueIds.get(0);
        }
        return leagueId;
    }

    public String getRecord()
    {
        String recordFormat = "%02d - %02d - %02d";
        return String.format(recordFormat, wins, losses, ties);
    }

    public TeamStatistic getTeamStatistic()
    {
        return teamStatistic;
    }

    public void setTeamStatistic(TeamStatistic teamStatistic)
    {
        this.teamStatistic = teamStatistic;
        this.wins = parseInt(teamStatistic.getWins());
        this.losses = parseInt(teamStatistic.getLosses());
        this.ties = parseInt(teamStatistic.getTies());
    }

    @Override
    public int compareTo(Team other)
    {
        int retVal = 0;
        if (this.points != other.points)
        {
            retVal = this.points < other.points ? 1 : -1;
        } else if (this.wins != other.wins)
        {
            retVal = this.wins < other.wins ? 1 : -1;
        } else if (this.goalsFor != other.goalsFor)
        {
            retVal = this.goalsFor < other.goalsFor ? 1 : -1;
        }
        return retVal;
    }

    @Override
    public String toString()
    {
        return this.getTitle() != null ? this.getTitle().getRendered() : super.toString();
    }

    public LeagueTable getLeagueTable()
    {
        return leagueTable;
    }

    public void setLeagueTable(LeagueTable leagueTable)
    {
        this.leagueTable = leagueTable;
        if (leagueTable != null)
        {
            this.setTeamStatistic(leagueTable.getTableData().get(String.valueOf(this.getId())));
        }
    }

    public String getNameFormatted(int maxChars)
    {
        return this.getName() == null ? "" : Utils.getFormatted(this.getName(), maxChars);
    }
}

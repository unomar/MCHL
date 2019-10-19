package com.sloppylinux.mchl.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Team extends Expirable implements Comparable<Team>, Serializable
{
	private static final long serialVersionUID = 1L;

	// Team info
	private String name;
	private List<Player> players;
	private Goalie goalie;
    private ArrayList<Game> schedule;
	private String captain;
	private String alternate1;
	private String alternate2;
	
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
		this.setName("");
		players = new ArrayList<Player>();
		schedule = new ArrayList<Game>();
		goalie = new Goalie();
	}
	
	public Team(String name)
	{
		this.setName(name);
		players = new ArrayList<Player>();
		schedule = new ArrayList<Game>();
		goalie = new Goalie();
	}
	
	/**
	 * @param name the name to set
	 */
	public void setName(String name)
	{
		this.name = name;
	}

	/**
	 * @return the name
	 */
	public String getName()
	{
		return name;
	}

	public void addPlayer(Player player)
	{
		players.add(player);
	}
	
	/**
	 * @param goalie the goalie to set
	 */
	public void setGoalie(Goalie goalie)
	{
		this.goalie = goalie;
	}

	/**
	 * @return the goalie
	 */
	public Goalie getGoalie()
	{
		return goalie;
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

	public void setRank(int rank)
	{
		this.rank = rank;
	}

	public int getRank()
	{
		return rank;
	}

	@Override
	public int compareTo(Team other)
	{
		int retVal = 0;
		if (this.points != other.points)
		{
			retVal = this.points < other.points ? 1 : -1;
		}
		else if (this.wins != other.wins)
		{
			retVal = this.wins < other.wins ? 1 : -1;
		}
		else if (this.goalsFor != other.goalsFor)
		{
			retVal = this.goalsFor < other.goalsFor ? 1 : -1;
		}
		return retVal;
	}
	
	@Override
	public String toString()
	{
		return this.name;
	}
}

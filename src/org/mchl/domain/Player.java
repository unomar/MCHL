package org.mchl.domain;

import java.io.Serializable;


public class Player extends Person implements Comparable<Player>, Serializable
{
	private static final long serialVersionUID = 1L;
	// Player info
	private int goals = 0;
	private int assists = 0;
	private int points = 0;
	private int pims = 0;

	public Player()
	{
		super();
	}

	public Player(String firstName, String lastName, int num, int gp, int g, int a, int p, int pim)
	{
		super(firstName, lastName, num, gp);
		this.goals = g;
		this.assists = a;
		this.points = p;
		this.pims = pim;
	}

	@Override
	public int compareTo(Player other)
	{
		// Reverse the sort order so we order from High to Low
		return new Integer(other.points).compareTo(new Integer(this.points));
	}

	

	/**
	 * @return the goals
	 */
	public int getGoals()
	{
		return goals;
	}

	/**
	 * @param goals
	 *            the goals to set
	 */
	public void setGoals(int goals)
	{
		this.goals = goals;
	}

	/**
	 * @return the assists
	 */
	public int getAssists()
	{
		return assists;
	}

	/**
	 * @param assists
	 *            the assists to set
	 */
	public void setAssists(int assists)
	{
		this.assists = assists;
	}

	/**
	 * @return the points
	 */
	public int getPoints()
	{
		return points;
	}

	/**
	 * @param points
	 *            the points to set
	 */
	public void setPoints(int points)
	{
		this.points = points;
	}

	/**
	 * @return the pims
	 */
	public int getPims()
	{
		return pims;
	}

	/**
	 * @param pims
	 *            the pims to set
	 */
	public void setPims(int pims)
	{
		this.pims = pims;
	}

	@Override
	public String toString()
	{
		return getName(" ");
	}
}

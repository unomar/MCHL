package com.sloppylinux.mchl.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Division domain object to store league division levels
 */
public class Division extends Expirable implements Serializable
{
	private static final long serialVersionUID = 1L;

	// Division info
	private String name;
	private ArrayList<Team> teams = new ArrayList<Team>();;
	
	public Division(String name)
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

	public void addTeam(Team team)
	{
		teams.add(team);
	}
	
	public ArrayList<Team> getTeams()
	{
		Collections.sort(teams);
		return teams;
	}
	
	@Override
	public String toString()
	{
		return this.name;
	}
}

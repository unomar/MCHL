package com.sloppylinux.mchl.domain;

import java.io.Serializable;

public class Goalie extends Person implements Serializable
{
    private static final long serialVersionUID = 1L;

    // Goalie info
    private int goalsAgainst;
    private int shots;
    private float gAA;
    private float savePercentage;

    public Goalie()
    {
        super();
    }

    public Goalie(String firstName, String lastName, int gamesPlayed, int goalsAgainst, int shots, float gaa, float savePercentage)
    {
        super(firstName, lastName, 0, gamesPlayed);
        this.goalsAgainst = goalsAgainst;
        this.shots = shots;
        this.gAA = gaa;
        this.savePercentage = savePercentage;
    }


    /**
     * @return the goalsAgainst
     */
    public int getGoalsAgainst()
    {
        return goalsAgainst;
    }

    /**
     * @return the shots
     */
    public int getShots()
    {
        return shots;
    }

    /**
     * @return the gAA
     */
    public float getgAA()
    {
        return gAA;
    }

    /**
     * @return the savePercentage
     */
    public float getSavePercentage()
    {
        return savePercentage;
    }
}

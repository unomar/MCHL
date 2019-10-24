package com.sloppylinux.mchl.domain;

import java.io.Serializable;

public abstract class Person extends Expirable implements Serializable
{
    private static final long serialVersionUID = 1L;
    private String firstName = "No";
    private String lastName = "Name";
    private int number = 0;
    private int gamesPlayed = 0;

    public Person()
    {
    }

    public Person(String firstName, String lastName, int num, int gp)
    {
        this.firstName = firstName;
        this.lastName = lastName;
        this.number = num;
        this.gamesPlayed = gp;
    }

    /**
     * @return the name
     */
    public String getName(String seperator)
    {
        return this.firstName + seperator + this.lastName;
    }

    /**
     * @param name the name to set
     */
    public void setName(String firstName, String lastName)
    {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    /**
     * @param firstName the Player's first name
     */
    public void setFirstName(String firstName)
    {
        this.firstName = firstName;
    }

    /**
     * @param lastName the Player's last name
     */
    public void setLastName(String lastName)
    {
        this.lastName = lastName;
    }

    /**
     * @return the number
     */
    public int getNumber()
    {
        return number;
    }

    /**
     * @param number the number to set
     */
    public void setNumber(int number)
    {
        this.number = number;
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

    @Override
    public String toString()
    {
        return this.firstName + " " + this.lastName;
    }
}

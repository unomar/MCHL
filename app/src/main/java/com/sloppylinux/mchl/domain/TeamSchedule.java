package com.sloppylinux.mchl.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class TeamSchedule extends Expirable implements Serializable
{
    private static final long serialVersionUID = 1L;

    private String name;
    private List<Game> games;

    public TeamSchedule()
    {
        setName("");
        setGames(new ArrayList<Game>());
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public List<Game> getGames()
    {
        return games;
    }

    public void setGames(List<Game> games)
    {
        this.games = games;
    }


}

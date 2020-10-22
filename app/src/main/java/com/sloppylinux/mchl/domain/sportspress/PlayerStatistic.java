package com.sloppylinux.mchl.domain.sportspress;

import com.google.gson.annotations.SerializedName;

public class PlayerStatistic implements Comparable<PlayerStatistic>
{
    @SerializedName("name")
    private String name;

    @SerializedName("g")
    private Integer goals;

    @SerializedName("a")
    private Integer assists;

    @SerializedName("pim")
    private Integer penaltyMins;

    @SerializedName("s")
    private Integer shots;

    @SerializedName("sa")
    private Integer shotsAgainst;

    @SerializedName("sv")
    private Integer sv;

    @SerializedName("gp")
    private Integer gamesPlayed;

    @SerializedName("win")
    private Integer wins;

    @SerializedName("loss")
    private Integer losses;

    @SerializedName("tie")
    private Integer ties;

    @SerializedName("overtimeloss")
    private Integer overtimeLosses;

    @SerializedName("p")
    private Integer points;

    @SerializedName("goalsfor")
    private Integer goalsFor;

    @SerializedName("ga")
    private Integer goalsAgainst;

    @SerializedName("ppg")
    private String ppg;

    @SerializedName("gaa")
    private String gaa;

    @SerializedName("ppgagainst")
    private Integer ppga;

    @SerializedName("ppoagainst")
    private String ppoa;

    @SerializedName("svpercent")
    private String savePercent;

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public Integer getGoals()
    {
        return goals;
    }

    public void setGoals(Integer goals)
    {
        this.goals = goals;
    }

    public Integer getAssists()
    {
        return assists;
    }

    public void setAssists(Integer assists)
    {
        this.assists = assists;
    }

    public Integer getPenaltyMins()
    {
        return penaltyMins;
    }

    public void setPenaltyMins(Integer penaltyMins)
    {
        this.penaltyMins = penaltyMins;
    }

    public Integer getShots()
    {
        return shots;
    }

    public void setShots(Integer shots)
    {
        this.shots = shots;
    }

    public Integer getShotsAgainst()
    {
        return shotsAgainst;
    }

    public void setShotsAgainst(Integer shotsAgainst)
    {
        this.shotsAgainst = shotsAgainst;
    }

    public Integer getSv()
    {
        return sv;
    }

    public void setSv(Integer sv)
    {
        this.sv = sv;
    }

    public Integer getGamesPlayed()
    {
        return gamesPlayed;
    }

    public void setGamesPlayed(Integer gamesPlayed)
    {
        this.gamesPlayed = gamesPlayed;
    }

    public Integer getWins()
    {
        return wins;
    }

    public void setWins(Integer wins)
    {
        this.wins = wins;
    }

    public Integer getLosses()
    {
        return losses;
    }

    public void setLosses(Integer losses)
    {
        this.losses = losses;
    }

    public Integer getTies()
    {
        return ties;
    }

    public void setTies(Integer ties)
    {
        this.ties = ties;
    }

    public Integer getOvertimeLosses()
    {
        return overtimeLosses;
    }

    public void setOvertimeLosses(Integer overtimeLosses)
    {
        this.overtimeLosses = overtimeLosses;
    }

    public Integer getPoints()
    {
        return points;
    }

    public void setPoints(Integer points)
    {
        this.points = points;
    }

    public Integer getGoalsFor()
    {
        return goalsFor;
    }

    public void setGoalsFor(Integer goalsFor)
    {
        this.goalsFor = goalsFor;
    }

    public Integer getGoalsAgainst()
    {
        return goalsAgainst;
    }

    public void setGoalsAgainst(Integer goalsAgainst)
    {
        this.goalsAgainst = goalsAgainst;
    }

    public String getPpg()
    {
        return ppg;
    }

    public void setPpg(String ppg)
    {
        this.ppg = ppg;
    }

    public String getGaa()
    {
        return gaa;
    }

    public void setGaa(String gaa)
    {
        this.gaa = gaa;
    }

    public Integer getPpga()
    {
        return ppga;
    }

    public void setPpga(Integer ppga)
    {
        this.ppga = ppga;
    }

    public String getPpoa()
    {
        return ppoa;
    }

    public void setPpoa(String ppoa)
    {
        this.ppoa = ppoa;
    }

    public String getSavePercent()
    {
        return savePercent;
    }

    public void setSavePercent(String savePercent)
    {
        this.savePercent = savePercent;
    }

    @Override
    public String toString()
    {
        return "PlayerStatistic{" +
                "name='" + name + '\'' +
                ", goals=" + goals +
                ", assists=" + assists +
                ", penaltyMins=" + penaltyMins +
                ", shots=" + shots +
                ", shotsAgainst=" + shotsAgainst +
                ", sv=" + sv +
                ", gamesPlayed=" + gamesPlayed +
                ", wins=" + wins +
                ", losses=" + losses +
                ", ties=" + ties +
                ", overtimeLosses=" + overtimeLosses +
                ", points=" + points +
                ", goalsFor=" + goalsFor +
                ", goalsAgainst=" + goalsAgainst +
                ", ppg='" + ppg + '\'' +
                ", gaa='" + gaa + '\'' +
                ", ppga=" + ppga +
                ", ppoa='" + ppoa + '\'' +
                ", savePercent='" + savePercent + '\'' +
                '}';
    }

    @Override
    public int compareTo(PlayerStatistic o)
    {
        // Handle null value for header row
        if (this.points == null)
        {
            return 1;
        }
        else if (o.getPoints() == null)
        {
            return -1;
        }
        return Integer.valueOf(this.points).compareTo(Integer.valueOf(o.getPoints()));
    }
}

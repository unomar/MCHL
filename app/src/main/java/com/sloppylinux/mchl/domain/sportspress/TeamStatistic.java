package com.sloppylinux.mchl.domain.sportspress;

import com.google.gson.annotations.SerializedName;
import com.sloppylinux.mchl.util.Utils;

public class TeamStatistic implements Comparable<TeamStatistic>
{
    @SerializedName("pos")
    private String position;

    @SerializedName("name")
    private String name;

    @SerializedName("gp")
    private String gamesPlayed;

    @SerializedName("w")
    private String wins;

    @SerializedName("l")
    private String losses;

    @SerializedName("t")
    private String ties;

    @SerializedName("ot")
    private String overtimeLosses;

    @SerializedName("pts")
    private String points;

    @SerializedName("gf")
    private String goalsFor;

    @SerializedName("ga")
    private String goalsAgainst;

    @SerializedName("diff")
    private String diff;

    @SerializedName("ppg")
    private String ppg;

    @SerializedName("ppo")
    private String ppo;

    @SerializedName("pppercent")
    private String powerPlayPercentage;

    @SerializedName("ppga")
    private String ppga;

    @SerializedName("ppoa")
    private String ppoa;

    @SerializedName("pkpercent")
    private String penaltyKillPercent;

    @SerializedName("lten")
    private String lastTen;

    @SerializedName("strk")
    private String streak;

    public String getPosition()
    {
        return position;
    }

    public void setPosition(String position)
    {
        this.position = position;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getGamesPlayed()
    {
        return gamesPlayed;
    }

    public void setGamesPlayed(String gamesPlayed)
    {
        this.gamesPlayed = gamesPlayed;
    }

    public String getWins()
    {
        return wins;
    }

    public void setWins(String wins)
    {
        this.wins = wins;
    }

    public String getLosses()
    {
        return losses;
    }

    public void setLosses(String losses)
    {
        this.losses = losses;
    }

    public String getTies()
    {
        return ties;
    }

    public void setTies(String ties)
    {
        this.ties = ties;
    }

    public String getOvertimeLosses()
    {
        return overtimeLosses;
    }

    public void setOvertimeLosses(String overtimeLosses)
    {
        this.overtimeLosses = overtimeLosses;
    }

    public String getPoints()
    {
        return points;
    }

    public void setPoints(String points)
    {
        this.points = points;
    }

    public String getGoalsFor()
    {
        return goalsFor;
    }

    public void setGoalsFor(String goalsFor)
    {
        this.goalsFor = goalsFor;
    }

    public String getGoalsAgainst()
    {
        return goalsAgainst;
    }

    public void setGoalsAgainst(String goalsAgainst)
    {
        this.goalsAgainst = goalsAgainst;
    }

    public String getDiff()
    {
        return diff;
    }

    public void setDiff(String diff)
    {
        this.diff = diff;
    }

    public String getPpg()
    {
        return ppg;
    }

    public void setPpg(String ppg)
    {
        this.ppg = ppg;
    }

    public String getPpo()
    {
        return ppo;
    }

    public void setPpo(String ppo)
    {
        this.ppo = ppo;
    }

    public String getPowerPlayPercentage()
    {
        return powerPlayPercentage;
    }

    public void setPowerPlayPercentage(String powerPlayPercentage)
    {
        this.powerPlayPercentage = powerPlayPercentage;
    }

    public String getPpga()
    {
        return ppga;
    }

    public void setPpga(String ppga)
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

    public String getPenaltyKillPercent()
    {
        return penaltyKillPercent;
    }

    public void setPenaltyKillPercent(String penaltyKillPercent)
    {
        this.penaltyKillPercent = penaltyKillPercent;
    }

    public String getLastTen()
    {
        return lastTen;
    }

    public void setLastTen(String lastTen)
    {
        this.lastTen = lastTen;
    }

    public String getStreak()
    {
        return streak;
    }

    public void setStreak(String streak)
    {
        this.streak = streak;
    }

    @Override
    public String toString()
    {
        return "TeamStatistic{" +
                "position='" + position + '\'' +
                ", name='" + name + '\'' +
                ", gamesPlayed='" + gamesPlayed + '\'' +
                ", wins='" + wins + '\'' +
                ", losses='" + losses + '\'' +
                ", ties='" + ties + '\'' +
                ", overtimeLosses='" + overtimeLosses + '\'' +
                ", points='" + points + '\'' +
                ", goalsFor='" + goalsFor + '\'' +
                ", goalsAgainst='" + goalsAgainst + '\'' +
                ", diff='" + diff + '\'' +
                ", ppg='" + ppg + '\'' +
                ", ppo='" + ppo + '\'' +
                ", powerPlayPercentage='" + powerPlayPercentage + '\'' +
                ", ppga='" + ppga + '\'' +
                ", ppoa='" + ppoa + '\'' +
                ", penaltyKillPercent='" + penaltyKillPercent + '\'' +
                ", lastTen='" + lastTen + '\'' +
                ", streak='" + streak + '\'' +
                '}';
    }

    @Override
    public int compareTo(TeamStatistic o)
    {
        return Integer.valueOf(Utils.safeParseInt(this.position)).compareTo(Integer.valueOf(Utils.safeParseInt(o.getPosition())));
    }
}

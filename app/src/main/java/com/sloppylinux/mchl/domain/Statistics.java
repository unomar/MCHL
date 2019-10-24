package com.sloppylinux.mchl.domain;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Statistics implements Comparable<Statistics>, Serializable
{
    @SerializedName("name")
    private String seasonName;

    @SerializedName("p")
    private int points;

    @Override
    public int compareTo(Statistics other)
    {
        return Integer.valueOf(points).compareTo(Integer.valueOf(other.points));
    }
}

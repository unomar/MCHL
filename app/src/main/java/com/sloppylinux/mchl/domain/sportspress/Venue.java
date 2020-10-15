package com.sloppylinux.mchl.domain.sportspress;

import com.google.gson.annotations.SerializedName;

public class Venue extends ResponseBase
{
    @SerializedName("name")
    private String name;

    @Override
    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }
}

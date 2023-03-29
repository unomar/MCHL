package com.sloppylinux.mchl.domain.sportspress;

import com.google.gson.annotations.SerializedName;


public class Season extends ResponseBase
{
    @SerializedName("description")
    private String description;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}

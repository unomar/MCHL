package com.sloppylinux.mchl.domain.sportspress;

import com.google.gson.annotations.SerializedName;

public class League extends ResponseBase
{
    @SerializedName("name")
    private String name;

    @SerializedName("slug")
    private String slug;

    @Override
    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }
}

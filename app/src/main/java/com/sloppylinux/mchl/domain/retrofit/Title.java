package com.sloppylinux.mchl.domain.retrofit;

import com.google.gson.annotations.SerializedName;

public class Title
{
    @SerializedName("rendered")
    private String rendered;

    public Title()
    {

    }

    public String getRendered() {
        return rendered;
    }

    public void setRendered(String rendered) {
        this.rendered = rendered;
    }
}

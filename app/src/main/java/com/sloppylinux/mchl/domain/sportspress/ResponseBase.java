package com.sloppylinux.mchl.domain.sportspress;

import com.google.gson.annotations.SerializedName;
import com.sloppylinux.mchl.domain.Expirable;

import java.util.Date;

public abstract class ResponseBase extends Expirable
{

    @SerializedName("id")
    private long id;

    @SerializedName("modified_gmt")
    private Date modifiedDate;

    @SerializedName("type")
    private String type;

    @SerializedName("title")
    private Title title;


    protected ResponseBase()
    {
    }

    public long getId()
    {
        return id;
    }

    public void setId(long id)
    {
        this.id = id;
    }

    public Date getModifiedDate()
    {
        return modifiedDate;
    }

    public void setModifiedDate(Date modifiedDate)
    {
        this.modifiedDate = modifiedDate;
    }

    public String getType()
    {
        return type;
    }

    public void setType(String type)
    {
        this.type = type;
    }

    public Title getTitle()
    {
        return title;
    }

    public void setTitle(Title title)
    {
        this.title = title;
    }

    public String getName()
    {
        return this.title == null ? "" : this.title.getRendered();
    }
}

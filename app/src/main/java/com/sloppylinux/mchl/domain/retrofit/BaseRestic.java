package com.sloppylinux.mchl.domain.retrofit;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

public abstract class BaseRestic {

    @SerializedName("id")
    private long id;

    @SerializedName("modified_gmt")
    private Date modifiedDate;

    @SerializedName("type")
    private String type;

    @SerializedName("title")
    private Title title;


    protected BaseRestic()
    {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(Date modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Title getTitle() {
        return title;
    }

    public void setTitle(Title title) {
        this.title = title;
    }
}

package com.sloppylinux.mchl.domain.sportspress;

import com.google.gson.annotations.SerializedName;

import java.util.Date;
import java.util.List;
import java.util.Map;

public class Event extends ResponseBase
{
    @SerializedName("venues")
    List<Long> venueIds;
    @SerializedName("teams")
    List<Long> teamIds;
    @SerializedName("main_results")
    List<String> results;
    @SerializedName("date")
    private Date eventDate;
    @SerializedName("status")
    private String status;
    @SerializedName("outcome")
    private Map<Long,String> outcome;

    public Event()
    {

    }

    public Date getEventDate()
    {
        return eventDate;
    }

    public void setEventDate(Date eventDate)
    {
        this.eventDate = eventDate;
    }

    public String getStatus()
    {
        return status;
    }

    public void setStatus(String status)
    {
        this.status = status;
    }

    public List<Long> getVenueIds()
    {
        return venueIds;
    }

    public void setVenueIds(List<Long> venueIds)
    {
        this.venueIds = venueIds;
    }

    public List<Long> getTeamIds()
    {
        return teamIds;
    }

    public void setTeamIds(List<Long> teamIds)
    {
        this.teamIds = teamIds;
    }

    public List<String> getResults()
    {
        return results;
    }

    public void setResults(List<String> results)
    {
        this.results = results;
    }

    public Map<Long, String> getOutcome() {
        return outcome;
    }

    public void setOutcome(Map<Long, String> outcome) {
        this.outcome = outcome;
    }
}

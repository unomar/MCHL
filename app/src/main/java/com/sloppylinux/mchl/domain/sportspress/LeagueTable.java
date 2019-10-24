package com.sloppylinux.mchl.domain.sportspress;

import com.google.gson.annotations.SerializedName;

import java.util.Map;

public class LeagueTable extends ResponseBase
{
    @SerializedName("data")
    private Map<String, TeamStatistic> tableData;

    public Map<String, TeamStatistic> getTableData()
    {
        return tableData;
    }

    public void setTableData(Map<String, TeamStatistic> tableData)
    {
        this.tableData = tableData;
    }
}

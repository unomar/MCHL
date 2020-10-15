package com.sloppylinux.mchl.domain.sportspress;

import com.google.gson.annotations.SerializedName;

import java.util.Map;

public class TeamTable extends ResponseBase
{
    @SerializedName("data")
    private Map<String, PlayerStatistic> tableData;

    public Map<String, PlayerStatistic> getTableData()
    {
        return tableData;
    }

    public void setTableData(Map<String, PlayerStatistic> tableData)
    {
        this.tableData = tableData;
    }
}

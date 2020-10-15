package com.sloppylinux.mchl.domain.sportspress;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.Map;

public class LeagueTable extends ResponseBase implements Parcelable
{
    @SerializedName("data")
    private Map<String, TeamStatistic> tableData;

    protected LeagueTable(Parcel in) {
        this.tableData = in.readHashMap(ClassLoader.getSystemClassLoader());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeMap(tableData);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<LeagueTable> CREATOR = new Creator<LeagueTable>() {
        @Override
        public LeagueTable createFromParcel(Parcel in) {
            return new LeagueTable(in);
        }

        @Override
        public LeagueTable[] newArray(int size) {
            return new LeagueTable[size];
        }
    };

    public Map<String, TeamStatistic> getTableData()
    {
        return tableData;
    }

    public void setTableData(Map<String, TeamStatistic> tableData)
    {
        this.tableData = tableData;
    }
}

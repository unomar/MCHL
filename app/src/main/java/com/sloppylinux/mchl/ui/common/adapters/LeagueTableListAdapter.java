package com.sloppylinux.mchl.ui.common.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.sloppylinux.mchl.domain.Team;
import com.sloppylinux.mchl.domain.sportspress.TeamStatistic;
import com.sloppylinux.mchl.ui.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class LeagueTableListAdapter extends ArrayAdapter<Team>{

    private List<Team> teamList;
    Context mContext;

    // View lookup cache
    private static class ViewHolder {
        ListView leagueTable;
    }

    public LeagueTableListAdapter(List<Team> data, Context context) {
        super(context, R.layout.homepage_team, data);
        this.teamList = data;
        this.mContext = context;
    }

    private int lastPosition = -1;

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Team teamModel = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder; // view lookup cache stored in tag

        final View result;

        if (convertView == null) {

            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.league_standings_row, parent, false);
            viewHolder.leagueTable = convertView.findViewById(R.id.league_standings_list);

            result=convertView;

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            result=convertView;
        }

        lastPosition = position;

        TeamStatisticListAdapter adapter = new TeamStatisticListAdapter(getTeamStatistics(teamModel), this.mContext);
        viewHolder.leagueTable.setAdapter(adapter);
        // Return the completed view to render on screen
        return result;
    }

    @NotNull
    private List<TeamStatistic> getTeamStatistics(Team playerTeam)
    {
        List<TeamStatistic> stats = new ArrayList<>();
        playerTeam.getLeagueTable().getTableData().forEach((k,v)->{
            stats.add(v);
        });
        return stats;
    }
}

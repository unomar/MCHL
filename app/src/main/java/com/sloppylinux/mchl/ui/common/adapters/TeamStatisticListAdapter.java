package com.sloppylinux.mchl.ui.common.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.sloppylinux.mchl.databinding.StandingsRowBinding;
import com.sloppylinux.mchl.domain.Team;
import com.sloppylinux.mchl.domain.sportspress.TeamStatistic;
import com.sloppylinux.mchl.R;
import com.sloppylinux.mchl.ui.common.viewholders.TeamStatisticViewHolder;
import com.sloppylinux.mchl.util.Config;
import com.sloppylinux.mchl.util.Utils;

import java.util.ArrayList;
import java.util.List;

public class TeamStatisticListAdapter extends ArrayAdapter<TeamStatistic>
{

    private List<TeamStatistic> teamList;
    private List<String> myTeams = new ArrayList<>();
    private Context mContext;
    private Config config;

    private int lastPosition = -1;

    public TeamStatisticListAdapter(List<TeamStatistic> data, Context context)
    {
        super(context, R.layout.standings_row, data);
        this.teamList = data;
        this.mContext = context;
        this.config = new Config(mContext);
        if (config != null && config.getPlayer() != null)
        {
            for (Team team : config.getPlayer().getPlayerTeams())
            {
                myTeams.add(team.getName());
            }
        }
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        // Get the data item for this position
        TeamStatistic teamModel = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        TeamStatisticViewHolder viewHolder; // view lookup cache stored in tag

        final View result;
        List<TextView> textViews = new ArrayList<>();

        if (convertView == null)
        {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.standings_row, parent, false);
            StandingsRowBinding standingsRowBinding = StandingsRowBinding.inflate(inflater, parent, false);
            viewHolder = new TeamStatisticViewHolder(convertView, standingsRowBinding);

            textViews.add(viewHolder.getTeamRank());
            textViews.add(viewHolder.getTeamName());
            textViews.add(viewHolder.getTeamGamesPlayed());
            textViews.add(viewHolder.getTeamWins());
            textViews.add(viewHolder.getTeamLosses());
            textViews.add(viewHolder.getTeamTies());
            textViews.add(viewHolder.getTeamPoints());
            textViews.add(viewHolder.getTeamGoalsFor());
            textViews.add(viewHolder.getTeamGoalsAgainst());

            result = convertView;

            convertView.setTag(viewHolder);
        } else
        {
            viewHolder = (TeamStatisticViewHolder) convertView.getTag();
            result = convertView;
        }
        lastPosition = position;

        int pos = Utils.safeParseInt(teamModel.getPosition());
        viewHolder.getTeamRank().setText((pos == -1) ? "#" : Integer.toString(pos));
        viewHolder.getTeamName().setText(Utils.getFormatted(teamModel.getName(), 24));
        viewHolder.getTeamGamesPlayed().setText(teamModel.getGamesPlayed());
        viewHolder.getTeamWins().setText(teamModel.getWins());
        viewHolder.getTeamLosses().setText(teamModel.getLosses());
        viewHolder.getTeamTies().setText(teamModel.getTies());
        viewHolder.getTeamPoints().setText(teamModel.getPoints());
        viewHolder.getTeamGoalsFor().setText(teamModel.getGoalsFor());
        viewHolder.getTeamGoalsAgainst().setText(teamModel.getGoalsAgainst());

        boolean isHeader = "Team".equals(teamModel.getName());
        boolean isMyTeam = myTeams.contains(teamModel.getName());
        int style = isHeader ? R.style.CodeFont_Table_Header : R.style.CodeFont_Table_Row;
        int color = isMyTeam ? R.color.colorHighlight : R.color.colorStandard;
        for (TextView textView : textViews)
        {
            textView.setTextAppearance(style);
            textView.setTextColor(ContextCompat.getColor(getContext(), color));
        }
        // Return the completed view to render on screen
        return result;
    }

    // View lookup cache

}

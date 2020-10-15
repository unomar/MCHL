package com.sloppylinux.mchl.ui.common.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.sloppylinux.mchl.domain.Team;
import com.sloppylinux.mchl.domain.sportspress.TeamStatistic;
import com.sloppylinux.mchl.ui.R;
import com.sloppylinux.mchl.util.Config;
import com.sloppylinux.mchl.util.Utils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

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
        ViewHolder viewHolder; // view lookup cache stored in tag

        final View result;
        List<TextView> textViews = new ArrayList<>();

        if (convertView == null)
        {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.standings_row, parent, false);
            viewHolder = new ViewHolder(convertView);

            textViews.add(viewHolder.teamRank);
            textViews.add(viewHolder.teamName);
            textViews.add(viewHolder.teamGamesPlayed);
            textViews.add(viewHolder.teamWins);
            textViews.add(viewHolder.teamLosses);
            textViews.add(viewHolder.teamTies);
            textViews.add(viewHolder.teamPoints);
            textViews.add(viewHolder.teamGoalsFor);
            textViews.add(viewHolder.teamGoalsAgainst);

            result = convertView;

            convertView.setTag(viewHolder);
        } else
        {
            viewHolder = (ViewHolder) convertView.getTag();
            result = convertView;
        }
        lastPosition = position;

        int pos = Utils.safeParseInt(teamModel.getPosition());
        viewHolder.teamRank.setText((pos == -1) ? "#" : Integer.toString(pos));
        viewHolder.teamName.setText(Utils.getFormatted(teamModel.getName(), 24));
        viewHolder.teamGamesPlayed.setText(teamModel.getGamesPlayed());
        viewHolder.teamWins.setText(teamModel.getWins());
        viewHolder.teamLosses.setText(teamModel.getLosses());
        viewHolder.teamTies.setText(teamModel.getTies());
        viewHolder.teamPoints.setText(teamModel.getPoints());
        viewHolder.teamGoalsFor.setText(teamModel.getGoalsFor());
        viewHolder.teamGoalsAgainst.setText(teamModel.getGoalsAgainst());

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
    static class ViewHolder
    {
        @BindView(R.id.standingsTeamRank)
        TextView teamRank;
        @BindView(R.id.standingsTeamName)
        TextView teamName;
        @BindView(R.id.standingsGamesPlayed)
        TextView teamGamesPlayed;
        @BindView(R.id.standingsWins)
        TextView teamWins;
        @BindView(R.id.standingsLosses)
        TextView teamLosses;
        @BindView(R.id.standingsTies)
        TextView teamTies;
        @BindView(R.id.standingsPoints)
        TextView teamPoints;
        @BindView(R.id.standingsGoalsFor)
        TextView teamGoalsFor;
        @BindView(R.id.standingsGoalsAgainst)
        TextView teamGoalsAgainst;

        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}

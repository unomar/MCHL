package com.sloppylinux.mchl.ui.common.viewholders;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.sloppylinux.mchl.ui.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TeamStatisticViewHolder extends RecyclerView.ViewHolder
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

    public TeamStatisticViewHolder(View view)
    {
        super(view);
        ButterKnife.bind(this, view);
    }

    public TextView getTeamRank()
    {
        return teamRank;
    }

    public TextView getTeamName()
    {
        return teamName;
    }

    public TextView getTeamGamesPlayed()
    {
        return teamGamesPlayed;
    }

    public TextView getTeamWins()
    {
        return teamWins;
    }

    public TextView getTeamLosses()
    {
        return teamLosses;
    }

    public TextView getTeamTies()
    {
        return teamTies;
    }

    public TextView getTeamPoints()
    {
        return teamPoints;
    }

    public TextView getTeamGoalsFor()
    {
        return teamGoalsFor;
    }

    public TextView getTeamGoalsAgainst()
    {
        return teamGoalsAgainst;
    }

    @BindView(R.id.standingsGoalsAgainst)
    TextView teamGoalsAgainst;
}

package com.sloppylinux.mchl.ui.common.viewholders;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.sloppylinux.mchl.databinding.StandingsRowBinding;

public class TeamStatisticViewHolder extends RecyclerView.ViewHolder
{
    private StandingsRowBinding binding;

    public TeamStatisticViewHolder(View view, StandingsRowBinding standingsRowBinding)
    {
        super(view);
        this.binding = standingsRowBinding;
    }

    public TextView getTeamRank()
    {
        return binding.standingsTeamRank;
    }

    public TextView getTeamName()
    {
        return binding.standingsTeamName;
    }

    public TextView getTeamGamesPlayed()
    {
        return binding.standingsGamesPlayed;
    }

    public TextView getTeamWins()
    {
        return binding.standingsWins;
    }

    public TextView getTeamLosses()
    {
        return binding.standingsLosses;
    }

    public TextView getTeamTies()
    {
        return binding.standingsTies;
    }

    public TextView getTeamPoints()
    {
        return binding.standingsPoints;
    }

    public TextView getTeamGoalsFor()
    {
        return binding.standingsGoalsFor;
    }

    public TextView getTeamGoalsAgainst()
    {
        return binding.standingsGoalsAgainst;
    }

}

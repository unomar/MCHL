package com.sloppylinux.mchl.ui.common.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.snackbar.Snackbar;
import com.sloppylinux.mchl.domain.Game;
import com.sloppylinux.mchl.domain.sportspress.LeagueTable;
import com.sloppylinux.mchl.domain.sportspress.TeamStatistic;
import com.sloppylinux.mchl.ui.common.views.MchlSnackbar;
import com.sloppylinux.mchl.util.Constants;
import com.sloppylinux.mchl.databinding.FragmentTeamComparisonBinding;

public class TeamComparisonFragment extends Fragment
{
    private FragmentTeamComparisonBinding binding;

    private View root;

    private MchlSnackbar snackbar;
    private TeamComparisonViewModel teamComparisonViewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState)
    {
        super.onCreateView(inflater, container, savedInstanceState);
        teamComparisonViewModel =
                new ViewModelProvider(this).get(TeamComparisonViewModel.class);
        binding = FragmentTeamComparisonBinding.inflate(inflater, container, false);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(View view, Bundle bundle)
    {
        super.onViewCreated(view, bundle);
        Game game = (Game)getArguments().get(Constants.GAME_KEY);

        getStatistics(game);
    }

    private void getStatistics(Game game)
    {
        snackbar = new MchlSnackbar(getView(), "Fetching stats...", Snackbar.LENGTH_INDEFINITE, getContext());
        snackbar.show();
        teamComparisonViewModel.getTeamTable(game.getHomeTeam()).observe((LifecycleOwner) this, new Observer<LeagueTable>()
        {
            @Override
            public void onChanged(@Nullable LeagueTable leagueTable)
            {
                snackbar.dismiss();
                TeamStatistic homeTeamStatistic = leagueTable.getTableData().get(String.valueOf(game.getHomeTeam().getId()));
                TeamStatistic awayTeamStatistic = leagueTable.getTableData().get(String.valueOf(game.getAwayTeam().getId()));
                updateTeamStats(homeTeamStatistic, awayTeamStatistic);
            }
        });
    }

    private void updateTeamStats(TeamStatistic homeTeam, TeamStatistic awayTeam)
    {
        binding.homeRank.setText(String.valueOf(homeTeam.getPosition()));
        binding.homeGP.setText(String.valueOf(homeTeam.getGamesPlayed()));
        binding.homeWins.setText(String.valueOf(homeTeam.getWins()));
        binding.homeTies.setText(String.valueOf(homeTeam.getTies()));
        binding.homeLosses.setText(String.valueOf(homeTeam.getLosses()));
        binding.homePoints.setText(String.valueOf(homeTeam.getPoints()));
        binding.homeGF.setText(String.valueOf(homeTeam.getGoalsFor()));
        binding.homeGA.setText(String.valueOf(homeTeam.getGoalsAgainst()));

        binding.awayRank.setText(String.valueOf(awayTeam.getPosition()));
        binding.awayGP.setText(String.valueOf(awayTeam.getGamesPlayed()));
        binding.awayWins.setText(String.valueOf(awayTeam.getWins()));
        binding.awayTies.setText(String.valueOf(awayTeam.getTies()));
        binding.awayLosses.setText(String.valueOf(awayTeam.getLosses()));
        binding.awayPoints.setText(String.valueOf(awayTeam.getPoints()));
        binding.awayGF.setText(String.valueOf(awayTeam.getGoalsFor()));
        binding.awayGA.setText(String.valueOf(awayTeam.getGoalsAgainst()));
    }
}

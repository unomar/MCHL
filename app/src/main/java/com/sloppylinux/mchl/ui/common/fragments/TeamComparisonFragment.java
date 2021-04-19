package com.sloppylinux.mchl.ui.common.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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
import com.sloppylinux.mchl.ui.R;
import com.sloppylinux.mchl.ui.common.views.MchlSnackbar;
import com.sloppylinux.mchl.util.Constants;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class TeamComparisonFragment extends Fragment
{
    @BindView(R.id.homeRank)
    TextView homeRank;
    @BindView(R.id.homeGP)
    TextView homeGamesPlayed;
    @BindView(R.id.homeWins)
    TextView homeWins;
    @BindView(R.id.homeTies)
    TextView homeTies;
    @BindView(R.id.homeLosses)
    TextView homeLosses;
    @BindView(R.id.homePoints)
    TextView homePoints;
    @BindView(R.id.homeGF)
    TextView homeGoalsFor;
    @BindView(R.id.homeGA)
    TextView homeGoalsAgainst;
    @BindView(R.id.awayRank)
    TextView awayRank;
    @BindView(R.id.awayGP)
    TextView awayGamesPlayed;
    @BindView(R.id.awayWins)
    TextView awayWins;
    @BindView(R.id.awayTies)
    TextView awayTies;
    @BindView(R.id.awayLosses)
    TextView awayLosses;
    @BindView(R.id.awayPoints)
    TextView awayPoints;
    @BindView(R.id.awayGF)
    TextView awayGoalsFor;
    @BindView(R.id.awayGA)
    TextView awayGoalsAgainst;
    private Unbinder unbinder;
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
        root = inflater.inflate(R.layout.fragment_team_comparison, container, false);
        unbinder = ButterKnife.bind(this, root);

        return root;
    }

    @Override
    public void onViewCreated(View view, Bundle bundle)
    {
        super.onViewCreated(view, bundle);
        Game game = (Game) getArguments().get(Constants.GAME_KEY);

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
        if (homeTeam != null)
        {
            homeRank.setText(String.valueOf(homeTeam.getPosition()));
            homeGamesPlayed.setText(String.valueOf(homeTeam.getGamesPlayed()));
            homeWins.setText(String.valueOf(homeTeam.getWins()));
            homeTies.setText(String.valueOf(homeTeam.getTies()));
            homeLosses.setText(String.valueOf(homeTeam.getLosses()));
            homePoints.setText(String.valueOf(homeTeam.getPoints()));
            homeGoalsFor.setText(String.valueOf(homeTeam.getGoalsFor()));
            homeGoalsAgainst.setText(String.valueOf(homeTeam.getGoalsAgainst()));
        }

        if (awayTeam != null)
        {
            awayRank.setText(String.valueOf(awayTeam.getPosition()));
            awayGamesPlayed.setText(String.valueOf(awayTeam.getGamesPlayed()));
            awayWins.setText(String.valueOf(awayTeam.getWins()));
            awayTies.setText(String.valueOf(awayTeam.getTies()));
            awayLosses.setText(String.valueOf(awayTeam.getLosses()));
            awayPoints.setText(String.valueOf(awayTeam.getPoints()));
            awayGoalsFor.setText(String.valueOf(awayTeam.getGoalsFor()));
            awayGoalsAgainst.setText(String.valueOf(awayTeam.getGoalsAgainst()));
        }
    }
}

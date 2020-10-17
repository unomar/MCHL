package com.sloppylinux.mchl.ui.common.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.snackbar.Snackbar;
import com.sloppylinux.mchl.domain.Team;
import com.sloppylinux.mchl.domain.sportspress.PlayerStatistic;
import com.sloppylinux.mchl.ui.R;
import com.sloppylinux.mchl.ui.common.adapters.TeamPlayersListAdapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class TeamFragment extends Fragment
{
    public static final String TEAM_KEY = "team";
    @BindView(R.id.teamPlayerList)
    ListView teamPlayerListView;

    private TeamViewModel teamViewModel;
    private Snackbar snackbar;
    private Unbinder unbinder;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState)
    {
        View root = inflater.inflate(R.layout.fragment_team, container, false);
        unbinder = ButterKnife.bind(this, root);
        teamViewModel =
                new ViewModelProvider(this).get(TeamViewModel.class);

        return root;
    }

    public void onViewCreated(View view, Bundle bundle)
    {
        super.onViewCreated(view, bundle);
        Team team = (Team) getArguments().get(TEAM_KEY);

        getPlayerStats(team.getId());
    }

    private void getPlayerStats(long teamId)
    {
        snackbar = Snackbar.make(getView(), "Fetching team stats...", Snackbar.LENGTH_INDEFINITE);
        snackbar.setAction("No action", null).show();
        teamViewModel.getTeam(teamId).observe((LifecycleOwner) this, new Observer<Team>()
        {
            @Override
            public void onChanged(@Nullable Team team)
            {
                snackbar.dismiss();
                updateTeamStats(team);
            }
        });
    }

    private void updateTeamStats(Team team)
    {
        List<PlayerStatistic> playerStatistics = new ArrayList<>();
        if (team != null && team.getTeamTable() != null)
        {
            for (Map.Entry<String, PlayerStatistic> entry : team.getTeamTable().getTableData().entrySet())
            {
                if (!"Player".equals(entry.getValue().getName()))
                {
                    playerStatistics.add(entry.getValue());
                }
            }
        }
        Collections.sort(playerStatistics, Collections.reverseOrder());
        TeamPlayersListAdapter teamPlayersListAdapter = new TeamPlayersListAdapter(playerStatistics, getContext());
        teamPlayerListView.setAdapter(teamPlayersListAdapter);
    }
}

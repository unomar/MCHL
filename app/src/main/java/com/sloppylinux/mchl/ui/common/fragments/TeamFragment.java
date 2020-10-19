package com.sloppylinux.mchl.ui.common.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModelProvider;

import com.sloppylinux.mchl.domain.Team;
import com.sloppylinux.mchl.domain.sportspress.PlayerStatistic;
import com.sloppylinux.mchl.ui.R;
import com.sloppylinux.mchl.ui.common.adapters.TeamPlayersListAdapter;
import com.sloppylinux.mchl.util.Constants;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class TeamFragment extends Fragment
{
    @BindView(R.id.teamPlayerList)
    ListView teamPlayerListView;
    @BindView(R.id.missingTeamData)
    TextView missingTeamData;

    private TeamViewModel teamViewModel;
    private Unbinder unbinder;
    private final Logger LOG = Logger.getLogger(TeamFragment.class.getName());

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState)
    {
        View root = inflater.inflate(R.layout.fragment_team, container, false);
        unbinder = ButterKnife.bind(this, root);
        teamViewModel =
                new ViewModelProvider(this).get(TeamViewModel.class);

        return root;
    }

    public void onViewCreated(@NotNull View view, Bundle bundle)
    {
        super.onViewCreated(view, bundle);
        if (getArguments() != null)
        {
            Team team = (Team) getArguments().get(Constants.TEAM_KEY);

            getPlayerStats(team.getId());
        }
        else
        {
            LOG.warning("Null arguments passed into TeamFragment.onViewCreated()");
        }
    }

    private void getPlayerStats(long teamId)
    {
        teamViewModel.getTeam(teamId).observe((LifecycleOwner) this, team -> updateTeamStats(team));
    }

    private void updateTeamStats(Team team)
    {
        List<PlayerStatistic> playerStatistics = new ArrayList<>();
        boolean hasData = false;
        if (team != null && team.getTeamTable() != null)
        {
            for (Map.Entry<String, PlayerStatistic> entry : team.getTeamTable().getTableData().entrySet())
            {
                    playerStatistics.add(entry.getValue());
                    hasData = true;
            }
            if (hasData)
            {
                missingTeamData.setVisibility(View.GONE);
            }
        }
        Collections.sort(playerStatistics, Collections.reverseOrder());
        TeamPlayersListAdapter teamPlayersListAdapter = new TeamPlayersListAdapter(playerStatistics, getContext());
        teamPlayerListView.setAdapter(teamPlayersListAdapter);
    }
}

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
import com.sloppylinux.mchl.R;
import com.sloppylinux.mchl.ui.common.adapters.TeamPlayersListAdapter;
import com.sloppylinux.mchl.util.Constants;
import com.sloppylinux.mchl.databinding.FragmentTeamBinding;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;


public class TeamFragment extends Fragment
{
    private FragmentTeamBinding binding;

    private TeamViewModel teamViewModel;
    private final Logger LOG = Logger.getLogger(TeamFragment.class.getName());

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState)
    {
        binding = FragmentTeamBinding.inflate(inflater, container, false);
        teamViewModel =
                new ViewModelProvider(this).get(TeamViewModel.class);

        return binding.getRoot();
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
                binding.missingTeamData.setVisibility(View.GONE);
            }
        }
        Collections.sort(playerStatistics, Collections.reverseOrder());
        TeamPlayersListAdapter teamPlayersListAdapter = new TeamPlayersListAdapter(playerStatistics, getContext());
        binding.teamPlayerList.setAdapter(teamPlayersListAdapter);
    }
}

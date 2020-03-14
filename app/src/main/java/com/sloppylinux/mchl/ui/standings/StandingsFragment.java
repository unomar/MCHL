package com.sloppylinux.mchl.ui.standings;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.sloppylinux.mchl.domain.Team;
import com.sloppylinux.mchl.domain.sportspress.TeamStatistic;
import com.sloppylinux.mchl.ui.R;
import com.sloppylinux.mchl.ui.common.adapters.TeamStatisticListAdapter;
import com.sloppylinux.mchl.ui.common.fragments.RefreshFragment;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class StandingsFragment extends RefreshFragment
{
    private TeamStatisticListAdapter adapter;

    private ListView standingsListView;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_standings, container, false);

        standingsListView = root.findViewById(R.id.standingsList);

        SwipeRefreshLayout refreshLayout = root.findViewById(R.id.standingsRefreshView);
        super.setup(refreshLayout);

        if (config.getPlayer() != null)
        {
            List<TeamStatistic> stats = getTeamStatistics();
            adapter = new TeamStatisticListAdapter(stats, getContext());
            standingsListView.setAdapter(adapter);
        }

        return root;
    }

    @NotNull
    private List<TeamStatistic> getTeamStatistics()
    {
        Team playerTeam = config.getPlayer().getPlayerTeams().get(0);
        List<TeamStatistic> stats = new ArrayList<>();
        playerTeam.getLeagueTable().getTableData().forEach((k,v)->{
            stats.add(v);
        });
        return stats;
    }

    @Override
    public void refreshView()
    {
        adapter.clear();
        adapter.addAll(getTeamStatistics());
        adapter.notifyDataSetChanged();
    }
}
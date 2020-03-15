package com.sloppylinux.mchl.ui.standings;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.sloppylinux.mchl.domain.sportspress.LeagueTable;
import com.sloppylinux.mchl.domain.sportspress.TeamStatistic;
import com.sloppylinux.mchl.ui.R;
import com.sloppylinux.mchl.ui.common.adapters.TeamStatisticListAdapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class LeagueStandingsFragment extends Fragment {
    public static final String ARG_OBJECT = "object";

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.league_standings_row, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        Bundle args = getArguments();
        LeagueTable leagueTable = args.getParcelable(ARG_OBJECT);
        List<TeamStatistic> teamStats = new ArrayList<>();
        leagueTable.getTableData().forEach((k,v)->{teamStats.add(v);});

        Collections.sort(teamStats);
        TeamStatisticListAdapter adapter = new TeamStatisticListAdapter(teamStats, getContext());
        ((ListView) view.findViewById(R.id.league_standings_list))
                .setAdapter(adapter);
    }
}
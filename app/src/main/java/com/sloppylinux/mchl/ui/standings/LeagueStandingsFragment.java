package com.sloppylinux.mchl.ui.standings;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.sloppylinux.mchl.domain.sportspress.LeagueTable;
import com.sloppylinux.mchl.domain.sportspress.TeamStatistic;
import com.sloppylinux.mchl.ui.common.adapters.TeamStatisticListAdapter;
import com.sloppylinux.mchl.databinding.LeagueStandingsRowBinding;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class LeagueStandingsFragment extends Fragment {
    public static final String ARG_OBJECT = "object";
    private LeagueStandingsRowBinding binding;


    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = LeagueStandingsRowBinding.inflate(inflater, container, true);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        Bundle args = getArguments();
        LeagueTable leagueTable = args.getParcelable(ARG_OBJECT);
        List<TeamStatistic> teamStats = new ArrayList<>();
        leagueTable.getTableData().forEach((k,v)->{teamStats.add(v);});

        Collections.sort(teamStats);
        TeamStatisticListAdapter adapter = new TeamStatisticListAdapter(teamStats, getContext());
        binding.leagueStandingsList.setAdapter(adapter);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
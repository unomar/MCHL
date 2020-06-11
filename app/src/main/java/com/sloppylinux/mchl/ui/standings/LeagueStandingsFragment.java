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

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class LeagueStandingsFragment extends Fragment {
    public static final String ARG_OBJECT = "object";
    private Unbinder unbinder;

    @BindView(R.id.league_standings_list)
    ListView leagueStandingsList;

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.league_standings_row, container, false);
        unbinder = ButterKnife.bind(this, root);

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        Bundle args = getArguments();
        LeagueTable leagueTable = args.getParcelable(ARG_OBJECT);
        List<TeamStatistic> teamStats = new ArrayList<>();
        leagueTable.getTableData().forEach((k,v)->{teamStats.add(v);});

        Collections.sort(teamStats);
        TeamStatisticListAdapter adapter = new TeamStatisticListAdapter(teamStats, getContext());
        leagueStandingsList.setAdapter(adapter);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }
}
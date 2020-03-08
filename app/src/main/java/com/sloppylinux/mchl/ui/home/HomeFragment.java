package com.sloppylinux.mchl.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.sloppylinux.mchl.ui.R;
import com.sloppylinux.mchl.ui.schedule.GameListAdapter;
import com.sloppylinux.mchl.util.Config;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        Config config = new Config(getContext());
        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
//        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                upcomingTextView.setText(s);
//            }
//        });
        ListView teamListView = root.findViewById(R.id.homepage_team_list);
        ListView scheduleListView = root.findViewById(R.id.homepage_schedule_list);
        ListView resultListView = root.findViewById(R.id.homepage_result_list);

        TeamListAdapter teamAdapter = new TeamListAdapter(config.getPlayer().getPlayerTeams(), getContext());
        teamListView.setAdapter(teamAdapter);

        GameListAdapter scheduleAdapter = new GameListAdapter(config.getPlayer().getPlayerGameList(), getContext());
        scheduleListView.setAdapter(scheduleAdapter);

        GameListAdapter resultAdapter = new GameListAdapter(config.getPlayer().getPlayerResultList(), getContext());
        resultListView.setAdapter(resultAdapter);
        return root;
    }
}
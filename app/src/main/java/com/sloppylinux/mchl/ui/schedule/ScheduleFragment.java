package com.sloppylinux.mchl.ui.schedule;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.sloppylinux.mchl.domain.Game;
import com.sloppylinux.mchl.ui.R;
import com.sloppylinux.mchl.ui.adapters.GameListAdapter;
import com.sloppylinux.mchl.util.Config;

import java.util.List;

public class ScheduleFragment extends Fragment {

    private ListView scheduleListView;

    private Config config;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        if (config == null) {
            config = new Config(this.getContext());
        }

        ScheduleViewModel scheduleViewModel =
                new ViewModelProvider(this).get(ScheduleViewModel.class);

        View root = inflater.inflate(R.layout.fragment_schedule, container, false);

        scheduleListView = root.findViewById(R.id.scheduleList);

        if (config.getPlayer() != null && !config.getPlayer().isExpired())
        {
            GameListAdapter adapter = new GameListAdapter(config.getPlayer().getPlayerGameList(), getContext());
            scheduleListView.setAdapter(adapter);
        }

        else {
            scheduleViewModel.getTeamScheduleData(config.getPlayer()).observe(getViewLifecycleOwner(), new Observer<List<Game>>() {
                @Override
                public void onChanged(List<Game> gameList) {

                    GameListAdapter adapter = new GameListAdapter(gameList, getContext());
                    scheduleListView.setAdapter(adapter);
                }
            });
        }

        return root;
    }
}
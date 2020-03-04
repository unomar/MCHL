package com.sloppylinux.mchl.ui.schedule;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.sloppylinux.mchl.domain.Game;
import com.sloppylinux.mchl.domain.Player;
import com.sloppylinux.mchl.domain.TeamSchedule;
import com.sloppylinux.mchl.gui.R;
import com.sloppylinux.mchl.util.Config;

import java.util.List;

public class ScheduleFragment extends Fragment {

    private ScheduleViewModel scheduleViewModel;

    private ListView scheduleListView;

    private Config config;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        config = new Config(this.getContext());

        scheduleViewModel =
                ViewModelProviders.of(this).get(ScheduleViewModel.class);

        View root = inflater.inflate(R.layout.fragment_schedule, container, false);

        scheduleListView = root.findViewById(R.id.scheduleList);

        final TextView textView = root.findViewById(R.id.text_schedule);
        scheduleViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });

        scheduleViewModel.getTeamScheduleData(config.getPlayer()).observe(this, new Observer<List<Game>>() {
            @Override
            public void onChanged(List<Game> gameList) {

                ArrayAdapter<Game> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, gameList);
                scheduleListView.setAdapter(adapter);
            }
        });

        return root;
    }
}
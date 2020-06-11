package com.sloppylinux.mchl.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.sloppylinux.mchl.domain.Player;
import com.sloppylinux.mchl.ui.R;
import com.sloppylinux.mchl.ui.common.adapters.GameListAdapter;
import com.sloppylinux.mchl.ui.common.adapters.TeamListAdapter;
import com.sloppylinux.mchl.ui.settings.SettingsViewModel;
import com.sloppylinux.mchl.util.Config;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class HomeFragment extends Fragment
{
    private SettingsViewModel settingsViewModel;
    private Config config;
    private Unbinder unbinder;

    @BindView(R.id.homeProgressBar)
    ProgressBar spinner;

    @BindView(R.id.homepage_team_list)
    ListView teamListView;

    @BindView(R.id.homepage_schedule_list)
    ListView scheduleListView;

    @BindView(R.id.homepage_result_list)
    ListView resultListView;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState)
    {

        config = new Config(getContext());
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        unbinder = ButterKnife.bind(this, root);

        settingsViewModel =
                new ViewModelProvider(this).get(SettingsViewModel.class);

        spinner.setVisibility(View.GONE);

        if (config.getPlayer() != null)
        {
            if (config.getPlayer().requiresUpdate())
            {
                updatePlayerInfo(config.getPlayer());
            } else
            {
                TeamListAdapter teamAdapter = new TeamListAdapter(config.getPlayer().getPlayerTeams(), getContext());
                teamListView.setAdapter(teamAdapter);

                GameListAdapter scheduleAdapter = new GameListAdapter(config.getPlayer().getPlayerGameList(), getContext());
                scheduleListView.setAdapter(scheduleAdapter);

                GameListAdapter resultAdapter = new GameListAdapter(config.getPlayer().getPlayerResultList(), getContext());
                resultListView.setAdapter(resultAdapter);
            }
        }

        return root;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    /**
     * Update player info.
     *
     * @param player The player to update
     */
    private void updatePlayerInfo(Player player)
    {
        spinner.setVisibility(View.VISIBLE);
        settingsViewModel.getPlayerInfo(player).observe(getViewLifecycleOwner(), new Observer<String>()
        {
            @Override
            public void onChanged(String s)
            {
                spinner.setVisibility(View.GONE);

                TeamListAdapter teamAdapter = new TeamListAdapter(config.getPlayer().getPlayerTeams(), getContext());
                teamListView.setAdapter(teamAdapter);

                GameListAdapter scheduleAdapter = new GameListAdapter(config.getPlayer().getPlayerGameList(), getContext());
                scheduleListView.setAdapter(scheduleAdapter);

                GameListAdapter resultAdapter = new GameListAdapter(config.getPlayer().getPlayerResultList(), getContext());
                resultListView.setAdapter(resultAdapter);
            }
        });
    }
}
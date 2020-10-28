package com.sloppylinux.mchl.ui.schedule;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.snackbar.Snackbar;
import com.sloppylinux.mchl.domain.Game;
import com.sloppylinux.mchl.domain.Player;
import com.sloppylinux.mchl.domain.TeamSchedule;
import com.sloppylinux.mchl.ui.R;
import com.sloppylinux.mchl.ui.common.adapters.GameListAdapter;
import com.sloppylinux.mchl.ui.common.fragments.GameFragment;
import com.sloppylinux.mchl.ui.common.views.MchlSnackbar;
import com.sloppylinux.mchl.util.Config;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class ScheduleFragment extends Fragment
{
    @BindView(R.id.scheduleList)
    ListView scheduleListView;
    @BindView(R.id.scheduleRefreshView)
    SwipeRefreshLayout refreshLayout;

    private RefreshScheduleViewModel refreshScheduleViewModel;
    private Unbinder unbinder;
    private GameListAdapter adapter;
    private Config config;
    private MchlSnackbar snackbar;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState)
    {
        if (config == null)
        {
            config = new Config(this.getContext());
        }

        View root = inflater.inflate(R.layout.fragment_schedule, container, false);
        unbinder = ButterKnife.bind(this, root);

        refreshScheduleViewModel = new ViewModelProvider(this).get(RefreshScheduleViewModel.class);
        refreshLayout.setOnRefreshListener(
                () -> updateSchedule(config.getPlayer())
        );

        return root;
    }

    @Override
    public void onViewCreated(@NotNull View view, Bundle bundle)
    {
        super.onViewCreated(view, bundle);
        if (config.getPlayer() != null)
        {
            adapter = new GameListAdapter(config.getPlayer().getPlayerGameList(), getContext());
            scheduleListView.setAdapter(adapter);
            scheduleListView.setOnItemClickListener((adapterView, v, index, arg3) ->
            {
                adapterView.callOnClick();
                Game game = (Game) adapterView.getItemAtPosition(index);

                FragmentManager fm = getActivity().getSupportFragmentManager();
                GameFragment gameFragment = GameFragment.newInstance(game);
                gameFragment.show(fm, "fragment_game_schedule");
            });
        }
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
        unbinder.unbind();
    }

    /**
     * Update player schedule.
     *
     * @param player The player to update
     */
    private void updateSchedule(Player player)
    {
        snackbar = new MchlSnackbar(getView(), "Updating schedule...", Snackbar.LENGTH_INDEFINITE, getContext());
        snackbar.show();
        refreshScheduleViewModel.getPlayerSchedule(player).observe(getViewLifecycleOwner(), s ->
        {
            refreshLayout.setRefreshing(false);
            snackbar.dismiss();

            adapter.clear();
            adapter.addAll(buildSchedule(s));
            adapter.notifyDataSetChanged();

            // Store the updated schedule to the cache/config
            config.getPlayer().setPlayerSchedule(s);
            config.storeValues();
        });
    }

    /**
     * Translate one to many team schedules into a unified list.
     * @param schedules The schedules to merge
     * @return A unified list of games
     */
    private List<Game> buildSchedule(List<TeamSchedule> schedules)
    {
        List<Game> gameList = new ArrayList<>();
        for (TeamSchedule schedule : schedules)
        {
            gameList.addAll(schedule.getGames());
        }
        Collections.sort(gameList);
        return gameList;
    }
}
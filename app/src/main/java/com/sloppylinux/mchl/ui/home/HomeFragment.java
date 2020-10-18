package com.sloppylinux.mchl.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.snackbar.Snackbar;
import com.sloppylinux.mchl.domain.Game;
import com.sloppylinux.mchl.domain.Player;
import com.sloppylinux.mchl.ui.R;
import com.sloppylinux.mchl.ui.common.adapters.GameListAdapter;
import com.sloppylinux.mchl.ui.common.adapters.TeamListAdapter;
import com.sloppylinux.mchl.ui.common.fragments.GameFragment;
import com.sloppylinux.mchl.ui.settings.SettingsViewModel;
import com.sloppylinux.mchl.util.Config;
import com.sloppylinux.mchl.util.Constants;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class HomeFragment extends Fragment
{
    @BindView(R.id.homeProgressBar)
    ProgressBar spinner;
    @BindView(R.id.homepage_team_list)
    ListView teamListView;
    @BindView(R.id.homepage_schedule_list)
    ListView scheduleListView;
    @BindView(R.id.homepage_result_list)
    ListView resultListView;
    private SettingsViewModel settingsViewModel;
    private Config config;
    private Unbinder unbinder;
    private Snackbar snackbar;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState)
    {

        config = new Config(getContext());
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        unbinder = ButterKnife.bind(this, root);

        settingsViewModel =
                new ViewModelProvider(this).get(SettingsViewModel.class);

        spinner.setVisibility(View.GONE);
        return root;
    }

    public void onViewCreated(View view, Bundle bundle)
    {

        if (config.getPlayer() != null)
        {
            if (config.getPlayer().requiresUpdate())
            {
                updatePlayerInfo(config.getPlayer());
            } else
            {
                TeamListAdapter teamAdapter = new TeamListAdapter(config.getPlayer().getPlayerTeams(), getContext());
                teamListView.setAdapter(teamAdapter);

                List<Game> playerGameList = trimGames(config.getPlayer().getPlayerGameList());

                GameListAdapter scheduleAdapter = new GameListAdapter(playerGameList, getContext());
                scheduleListView.setAdapter(scheduleAdapter);
                scheduleListView.setOnItemClickListener((adapterView, v, index, arg3) ->
                {
                    adapterView.callOnClick();
                    Game game = (Game) adapterView.getItemAtPosition(index);

                    FragmentManager fm = getActivity().getSupportFragmentManager();
                    GameFragment gameFragment = GameFragment.newInstance(game);
                    gameFragment.show(fm, "fragment_game_schedule");
                });

                List<Game> playerResultList = trimGames(config.getPlayer().getPlayerResultList());
                GameListAdapter resultAdapter = new GameListAdapter(playerResultList, getContext());
                resultListView.setAdapter(resultAdapter);
                resultListView.setOnItemClickListener((adapterView, v, index, arg3) ->
                {
                    adapterView.callOnClick();
                    Game game = (Game) adapterView.getItemAtPosition(index);

                    // Create a GameResultFragment and display the Dialog
                    FragmentManager fm = getActivity().getSupportFragmentManager();
                    GameFragment gameResultFragment = GameFragment.newInstance(game);
                    gameResultFragment.show(fm, "fragment_game_result");
                });
            }
        }
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
        unbinder.unbind();
    }

    /**
     * Trim the list of games (Schedule & Results) to the defined max
     * @param gameList The list to trim
     * @return The trimmed list
     */
    private List<Game> trimGames(List<Game> gameList)
    {
        if (gameList.size() > Constants.MAX_GAMES_HOME)
        {
            gameList = gameList.subList(0,Constants.MAX_GAMES_HOME);
        }
        return gameList;
    }

    /**
     * Update player info.
     *
     * @param player The player to update
     */
    private void updatePlayerInfo(Player player)
    {
        snackbar = Snackbar.make(getView(), "Fetching updated schedule and stats for " + player.getName(" "), Snackbar.LENGTH_INDEFINITE);
        snackbar.setAction("No action", null).show();
        spinner.setVisibility(View.VISIBLE);
        settingsViewModel.getPlayerInfo(player).observe(getViewLifecycleOwner(), s ->
        {
            spinner.setVisibility(View.GONE);
            snackbar.dismiss();

            TeamListAdapter teamAdapter = new TeamListAdapter(config.getPlayer().getPlayerTeams(), getContext());
            teamListView.setAdapter(teamAdapter);

            List<Game> playerGameList = trimGames(config.getPlayer().getPlayerGameList());
            GameListAdapter scheduleAdapter = new GameListAdapter(playerGameList, getContext());
            scheduleListView.setAdapter(scheduleAdapter);

            List<Game> playerResultList = trimGames(config.getPlayer().getPlayerResultList());
            GameListAdapter resultAdapter = new GameListAdapter(playerResultList, getContext());
            resultListView.setAdapter(resultAdapter);
        });
    }
}
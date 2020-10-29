package com.sloppylinux.mchl.ui.results;

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

public class ResultsFragment extends Fragment
{
    @BindView(R.id.resultList)
    ListView resultListView;
    @BindView(R.id.resultRefreshView)
    SwipeRefreshLayout refreshLayout;

    private RefreshResultsViewModel refreshResultsViewModel;
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

        View root = inflater.inflate(R.layout.fragment_results, container, false);
        unbinder = ButterKnife.bind(this, root);

        refreshResultsViewModel = new ViewModelProvider(this).get(RefreshResultsViewModel.class);
        refreshLayout.setColorSchemeResources(R.color.colorAccent, R.color.colorPrimaryDark);
        refreshLayout.setOnRefreshListener(
                () -> updateResults(config.getPlayer())
        );

        return root;
    }

    @Override
    public void onViewCreated(@NotNull View view, Bundle bundle)
    {
        super.onViewCreated(view, bundle);
        if (config.getPlayer() != null)
        {
            adapter = new GameListAdapter(config.getPlayer().getPlayerResultList(), getContext());
            resultListView.setAdapter(adapter);
            resultListView.setOnItemClickListener((adapterView, v, index, arg3) ->
            {
                adapterView.callOnClick();
                Game game = (Game) adapterView.getItemAtPosition(index);

                FragmentManager fm = getActivity().getSupportFragmentManager();
                GameFragment gameResultFragment = GameFragment.newInstance(game);
                gameResultFragment.show(fm, "fragment_game_result");
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
     * Update player results.
     *
     * @param player The player to update
     */
    private void updateResults(Player player)
    {
        snackbar = new MchlSnackbar(getView(), "Updating recent results...", Snackbar.LENGTH_INDEFINITE, getContext());
        snackbar.show();
        refreshResultsViewModel.getPlayerSchedule(player).observe(getViewLifecycleOwner(), s ->
        {
            refreshLayout.setRefreshing(false);
            snackbar.dismiss();

            adapter.clear();
            adapter.addAll(buildResults(s));
            adapter.notifyDataSetChanged();

            config.getPlayer().setPlayerResults(s);
            config.storeValues();
        });
    }

    /**
     * Translate one to many team results into a unified list.
     *
     * @param resultsList The results to merge
     * @return A unified list of games
     */
    private List<Game> buildResults(List<TeamSchedule> resultsList)
    {
        List<Game> gameList = new ArrayList<>();
        for (TeamSchedule results : resultsList)
        {
            gameList.addAll(results.getGames());
        }
        gameList.sort(Collections.reverseOrder());
        return gameList;
    }
}
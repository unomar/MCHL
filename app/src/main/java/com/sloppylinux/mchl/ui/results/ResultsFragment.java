package com.sloppylinux.mchl.ui.results;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.sloppylinux.mchl.domain.Game;
import com.sloppylinux.mchl.ui.R;
import com.sloppylinux.mchl.ui.common.adapters.GameListAdapter;
import com.sloppylinux.mchl.ui.common.fragments.GameFragment;
import com.sloppylinux.mchl.ui.common.fragments.RefreshFragment;
import com.sloppylinux.mchl.util.Config;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ResultsFragment extends RefreshFragment
{
    @BindView(R.id.resultList)
    ListView resultListView;
    @BindView(R.id.resultRefreshView)
    SwipeRefreshLayout refreshLayout;

    private GameListAdapter adapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState)
    {

        if (config == null)
        {
            config = new Config(this.getContext());
        }

        View root = inflater.inflate(R.layout.fragment_results, container, false);
        unbinder = ButterKnife.bind(this, root);
        super.setup(refreshLayout);

        return root;
    }

    @Override
    public void onViewCreated(View view, Bundle bundle)
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
    public void refreshView()
    {
        adapter.clear();
        adapter.addAll(config.getPlayer().getPlayerResultList());
        adapter.notifyDataSetChanged();
    }
}
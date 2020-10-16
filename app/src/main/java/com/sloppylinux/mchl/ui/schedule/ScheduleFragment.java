package com.sloppylinux.mchl.ui.schedule;

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

public class ScheduleFragment extends RefreshFragment
{
    @BindView(R.id.scheduleList)
    ListView scheduleListView;
    @BindView(R.id.scheduleRefreshView)
    SwipeRefreshLayout refreshLayout;
    private GameListAdapter adapter;
    private Config config;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState)
    {
        if (config == null)
        {
            config = new Config(this.getContext());
        }

        View root = inflater.inflate(R.layout.fragment_schedule, container, false);
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
    public void refreshView()
    {
        adapter.clear();
        adapter.addAll(config.getPlayer().getPlayerGameList());
        adapter.notifyDataSetChanged();
    }

}
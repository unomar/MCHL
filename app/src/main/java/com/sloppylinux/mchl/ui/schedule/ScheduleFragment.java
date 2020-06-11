package com.sloppylinux.mchl.ui.schedule;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.sloppylinux.mchl.ui.R;
import com.sloppylinux.mchl.ui.common.adapters.GameListAdapter;
import com.sloppylinux.mchl.ui.common.fragments.RefreshFragment;
import com.sloppylinux.mchl.util.Config;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ScheduleFragment extends RefreshFragment
{
    private GameListAdapter adapter;
    private Config config;

    @BindView(R.id.scheduleList)
    ListView scheduleListView;

    @BindView(R.id.scheduleRefreshView)
    SwipeRefreshLayout refreshLayout;



    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        if (config == null) {
            config = new Config(this.getContext());
        }

        View root = inflater.inflate(R.layout.fragment_schedule, container, false);
        unbinder = ButterKnife.bind(this, root);
        super.setup(refreshLayout);

        if (config.getPlayer() != null)
        {
            adapter = new GameListAdapter(config.getPlayer().getPlayerGameList(), getContext());
            scheduleListView.setAdapter(adapter);
        }

        return root;
    }

    @Override
    public void refreshView()
    {
        adapter.clear();
        adapter.addAll(config.getPlayer().getPlayerGameList());
        adapter.notifyDataSetChanged();
    }

}
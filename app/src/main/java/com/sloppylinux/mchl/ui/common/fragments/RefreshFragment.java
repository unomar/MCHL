package com.sloppylinux.mchl.ui.common.fragments;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.sloppylinux.mchl.domain.Player;
import com.sloppylinux.mchl.util.Config;

public abstract class RefreshFragment extends Fragment
{
    protected SwipeRefreshLayout mySwipeRefreshLayout;

    protected RefreshViewModel refreshViewModel;

    protected Config config;


    /**
     * Setup the swipe refresh layout components
     * @param swipeRefreshLayout The layout to attach to
     */
    public void setup(SwipeRefreshLayout swipeRefreshLayout)
    {
        mySwipeRefreshLayout = swipeRefreshLayout;
        refreshViewModel = new ViewModelProvider(this).get(RefreshViewModel.class);
        config = new Config(getContext());
        mySwipeRefreshLayout.setOnRefreshListener(
                () -> updatePlayerInfo(config.getPlayer())
        );
    }

    /**
     * Update player info.
     * @param player The player to update
     */
    private void updatePlayerInfo(Player player)
    {
        refreshViewModel.getPlayerInfo(player).observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                refreshView();
                mySwipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    /**
     * Method used for implementation classes to update their data views;
     */
    public abstract void refreshView();
}

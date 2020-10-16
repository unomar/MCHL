package com.sloppylinux.mchl.ui.common.fragments;

import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.sloppylinux.mchl.domain.Player;
import com.sloppylinux.mchl.util.Config;
import com.sloppylinux.mchl.util.WebserviceException;

import butterknife.Unbinder;

public abstract class RefreshFragment extends Fragment
{
    protected SwipeRefreshLayout mySwipeRefreshLayout;

    protected RefreshViewModel refreshViewModel;

    protected Config config;

    protected Unbinder unbinder;


    /**
     * Setup the swipe refresh layout components
     *
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
     *
     * @param player The player to update
     */
    private void updatePlayerInfo(Player player)
    {
        refreshViewModel.getPlayerInfo(player).observe(getViewLifecycleOwner(), s ->
        {
            mySwipeRefreshLayout.setRefreshing(false);
            if (s.startsWith(WebserviceException.ERROR_PREFIX))
            {
                Toast toast = Toast.makeText(getContext(), s, Toast.LENGTH_LONG);
                toast.show();
            } else
            {
                refreshView();
            }
        });
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
        unbinder.unbind();
    }

    /**
     * Method used for implementation classes to update their data views;
     */
    public abstract void refreshView();
}

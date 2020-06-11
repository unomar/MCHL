package com.sloppylinux.mchl.ui.standings;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.sloppylinux.mchl.ui.R;
import com.sloppylinux.mchl.ui.common.fragments.RefreshFragment;
import com.sloppylinux.mchl.util.Config;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StandingsFragment extends RefreshFragment
{
    @BindView(R.id.standings_pager)
    ViewPager viewPager;
    @BindView(R.id.tab_layout)
    TabLayout tabLayout;

    private StandingsPagerAdapter standingsPagerAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View root = inflater.inflate(R.layout.fragment_standings, container, false);
        unbinder = ButterKnife.bind(this, root);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        Config config = new Config(getContext());
        if (config.getLeagueTables() != null)
        {
            standingsPagerAdapter = new StandingsPagerAdapter(config.getLeagueTables(), getChildFragmentManager());
            viewPager.setAdapter(standingsPagerAdapter);

            tabLayout.setupWithViewPager(viewPager);
        }
    }

    @Override
    public void refreshView()
    {
//        adapter.clear();
//        adapter.addAll(config.getPlayer().getPlayerTeams());
//        adapter.notifyDataSetChanged();
    }
}



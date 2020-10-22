package com.sloppylinux.mchl.ui.standings;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.sloppylinux.mchl.domain.sportspress.LeagueTable;

import java.util.List;

public class StandingsPagerAdapter extends FragmentStatePagerAdapter
{
    private List<LeagueTable> leagueTables;

    public StandingsPagerAdapter(List<LeagueTable> leagueTables, FragmentManager fm)
    {
        super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        this.leagueTables = leagueTables;
    }

    @Override
    public Fragment getItem(int i) {
        LeagueTable leagueTable = leagueTables.get(i);
        Fragment fragment = new LeagueStandingsFragment();
        Bundle args = new Bundle();
        args.putParcelable(LeagueStandingsFragment.ARG_OBJECT, leagueTable);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getCount() {
        return leagueTables.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return leagueTables.get(position).getName();
    }
}

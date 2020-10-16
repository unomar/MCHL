package com.sloppylinux.mchl.ui.common.adapters;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.sloppylinux.mchl.domain.Game;
import com.sloppylinux.mchl.ui.common.fragments.TeamComparisonFragment;
import com.sloppylinux.mchl.ui.common.fragments.TeamFragment;

import java.util.Arrays;
import java.util.List;

public class GamePagerAdapter extends FragmentStatePagerAdapter
{
    private Game game;
    // TODO: Update to use strings.xml for Home/VS/Away lookup
    private List<String> tabNames = Arrays.asList("Home", "VS", "Away");

    public GamePagerAdapter(Game game, FragmentManager fm)
    {
        super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        this.game = game;

    }

    @Override
    public Fragment getItem(int position)
    {
        Fragment fragment;
        Bundle bundle = new Bundle();
        switch (position)
        {
            case 0: // Home Team
                fragment = new TeamFragment();
                bundle.putSerializable(TeamFragment.TEAM_KEY, game.getHomeTeam());
                break;
            case 2: // Away Team
                fragment = new TeamFragment();
                bundle.putSerializable(TeamFragment.TEAM_KEY, game.getAwayTeam());
                break;
            default: // Comparison
                fragment = new TeamComparisonFragment();
                bundle.putSerializable(TeamComparisonFragment.GAME_KEY, game);
                break;
        }

        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public int getCount()
    {
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position)
    {
        return tabNames.get(position);
    }
}

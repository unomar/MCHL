package com.sloppylinux.mchl.ui.common.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.sloppylinux.mchl.domain.Game;
import com.sloppylinux.mchl.ui.R;
import com.sloppylinux.mchl.ui.common.adapters.GamePagerAdapter;
import com.sloppylinux.mchl.util.Constants;

import org.jetbrains.annotations.NotNull;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A DialogFragment which displays a single upcoming game.
 */
public class GameFragment extends DialogFragment
{
    protected Unbinder unbinder;
    @BindView(R.id.gameDate)
    TextView gameDate;
    @BindView(R.id.gameLocation)
    TextView gameLocation;
    @BindView(R.id.gameHomeName)
    TextView homeTeamName;
    @BindView(R.id.gameHomeTeamScore)
    TextView homeTeamScore;
    @BindView(R.id.gameAwayName)
    TextView awayTeamName;
    @BindView(R.id.gameAwayTeamScore)
    TextView awayTeamScore;
    @BindView(R.id.gamePager)
    ViewPager viewPager;
    @BindView(R.id.gameTabs)
    TabLayout tabLayout;

    public GameFragment()
    {

    }

    public static GameFragment newInstance(Game game)
    {
        GameFragment gameFragment = new GameFragment();
        Bundle args = new Bundle();
        args.putSerializable(Constants.GAME_KEY, game);
        gameFragment.setArguments(args);

        return gameFragment;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState)
    {
        View root = inflater.inflate(R.layout.fragment_game, container, false);
        unbinder = ButterKnife.bind(this, root);

        return root;
    }

    @Override
    public void onViewCreated(@NotNull View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
        Game game = (Game) getArguments().get(Constants.GAME_KEY);

        if (game != null)
        {
            gameDate.setText(game.getDateString());
            gameLocation.setText(game.getLocation());
            homeTeamName.setText(game.getHomeTeamName());
            awayTeamName.setText(game.getAwayTeamName());

            String homeScore = "";
            String awayScore = "";
            if (!game.isInFuture())
            {
                homeScore = String.valueOf(game.getHomeScore());
                awayScore = String.valueOf(game.getAwayScore());
            }
            homeTeamScore.setText(homeScore);
            awayTeamScore.setText(awayScore);

            GamePagerAdapter gamePagerAdapter = new GamePagerAdapter(game, getChildFragmentManager());
            viewPager.setAdapter(gamePagerAdapter);

            tabLayout.setupWithViewPager(viewPager);
            tabLayout.selectTab(tabLayout.getTabAt(1));
        }
    }


    @Override
    public void onDestroy()
    {
        super.onDestroy();
        unbinder.unbind();
    }
}
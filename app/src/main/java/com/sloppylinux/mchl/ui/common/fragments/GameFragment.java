package com.sloppylinux.mchl.ui.common.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.sloppylinux.mchl.databinding.FragmentGameBinding;
import com.sloppylinux.mchl.domain.Game;
import com.sloppylinux.mchl.ui.common.adapters.GamePagerAdapter;
import com.sloppylinux.mchl.util.Constants;

import org.jetbrains.annotations.NotNull;

/**
 * A DialogFragment which displays a single upcoming game.
 */
public class GameFragment extends DialogFragment
{
    private FragmentGameBinding binding;

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
//        View root = inflater.inflate(R.layout.fragment_game, container, false);
        binding = FragmentGameBinding.inflate(inflater,container, false);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NotNull View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
        Game game = (Game) getArguments().get(Constants.GAME_KEY);

        if (game != null)
        {
            binding.gameDate.setText(game.getDateString());
            binding.gameLocation.setText(game.getLocation());
            binding.gameHomeName.setText(game.getHomeTeamName());
            binding.gameAwayName.setText(game.getAwayTeamName());

            String homeScore = "";
            String awayScore = "";
            if (!game.isInFuture())
            {
                homeScore = String.valueOf(game.getHomeScore());
                awayScore = String.valueOf(game.getAwayScore());
            }
            binding.gameHomeTeamScore.setText(homeScore);
            binding.gameAwayTeamScore.setText(awayScore);

            GamePagerAdapter gamePagerAdapter = new GamePagerAdapter(game, getChildFragmentManager());
            binding.gamePager.setAdapter(gamePagerAdapter);

            binding.gameTabs.setupWithViewPager(binding.gamePager);
            binding.gameTabs.selectTab(binding.gameTabs.getTabAt(1));
        }
    }


    @Override
    public void onDestroy()
    {
        super.onDestroy();
    }
}
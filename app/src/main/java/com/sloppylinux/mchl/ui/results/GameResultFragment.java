package com.sloppylinux.mchl.ui.results;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.sloppylinux.mchl.domain.Game;
import com.sloppylinux.mchl.ui.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class GameResultFragment extends Fragment
{
    protected Unbinder unbinder;
    @BindView(R.id.gameResultView)
    RelativeLayout gameResultView;
    @BindView(R.id.gameResultDate)
    TextView gameResultDate;
    @BindView(R.id.gameResultLocation)
    TextView gameResultLocation;
    @BindView(R.id.gameResultHomeName)
    TextView homeTeamName;
    @BindView(R.id.gameResultAwayName)
    TextView awayTeamName;
    @BindView(R.id.gameResultHomeTeamScore)
    TextView homeTeamScore;
    @BindView(R.id.gameResultAwayTeamScore)
    TextView awayTeamScore;

    View root;
    private Game game;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState)
    {
        root = inflater.inflate(R.layout.fragment_game_result, container, false);

        unbinder = ButterKnife.bind(this, root);

        root.setVisibility(View.VISIBLE);
        root.bringToFront();

        if (game != null)
        {
            // TODO: Populate game info
            gameResultDate.setText(game.getShortDateString());
            gameResultLocation.setText(game.getLocation());
            homeTeamName.setText(game.getHome());
            homeTeamScore.setText(String.valueOf(game.getHomeScore()));
            awayTeamName.setText(game.getAway());
            awayTeamScore.setText(String.valueOf(game.getAwayScore()));
        }

        return root;
    }


    @Override
    public void onDestroy()
    {
        root.setVisibility(View.GONE);
        super.onDestroy();
        unbinder.unbind();
    }


    public void setGame(Game game)
    {
        this.game = game;
    }
}
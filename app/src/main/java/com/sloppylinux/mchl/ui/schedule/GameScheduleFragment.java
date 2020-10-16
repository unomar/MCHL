package com.sloppylinux.mchl.ui.schedule;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.sloppylinux.mchl.domain.Game;
import com.sloppylinux.mchl.ui.R;

import org.jetbrains.annotations.NotNull;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A DialogFragment which displays a single upcoming game.
 */
public class GameScheduleFragment extends DialogFragment
{
    protected Unbinder unbinder;
    @BindView(R.id.gameScheduleDate)
    TextView gameScheduleDate;
    @BindView(R.id.gameScheduleLocation)
    TextView gameScheduleLocation;
    @BindView(R.id.gameScheduleHomeName)
    TextView homeTeamName;
    @BindView(R.id.gameScheduleAwayName)
    TextView awayTeamName;

    public GameScheduleFragment()
    {

    }

    public static GameScheduleFragment newInstance(Game game)
    {
        GameScheduleFragment frag = new GameScheduleFragment();
        Bundle args = new Bundle();
        args.putSerializable("game", game);
        frag.setArguments(args);

        return frag;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState)
    {
        View root = inflater.inflate(R.layout.fragment_game_schedule, container, false);
        unbinder = ButterKnife.bind(this, root);

        return root;
    }

    @Override
    public void onViewCreated(@NotNull View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
        Game game = (Game) getArguments().get("game");

        if (game != null)
        {
            gameScheduleDate.setText(game.getShortDateString());
            gameScheduleLocation.setText(game.getLocation());
            homeTeamName.setText(game.getHome());
            awayTeamName.setText(game.getAway());

            // TODO: Populate team info
        }
    }


    @Override
    public void onDestroy()
    {
        super.onDestroy();
        unbinder.unbind();
    }
}
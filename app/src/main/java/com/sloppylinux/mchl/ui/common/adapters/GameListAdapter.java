package com.sloppylinux.mchl.ui.common.adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;
import com.sloppylinux.mchl.domain.Game;
import com.sloppylinux.mchl.ui.R;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class GameListAdapter extends ArrayAdapter<Game> implements View.OnClickListener {

    private List<Game> gameList;
    Context mContext;

    // View lookup cache
    private static class ViewHolder {
        TextView gameDate;
        TextView gameLocation;
        TextView homeTeam;
        TextView awayTeam;
        TextView vsScore;
    }

    public GameListAdapter(List<Game> data, Context context) {
        super(context, R.layout.schedule_game_row, data);
        this.gameList = data;
        this.mContext = context;
    }

    @Override
    public void onClick(View v) {

        int position = (Integer) v.getTag();
        Object object = getItem(position);
        Game game = (Game) object;

        // Do Stuff

        Snackbar.make(v, "Loading Match Info for " + game.getDateString(), Snackbar.LENGTH_LONG)
                .setAction("No action", null).show();

    }

    private int lastPosition = -1;

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Game gameModel = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder; // view lookup cache stored in tag

        final View result = getGameView(position, convertView, parent, gameModel);
        // Return the completed view to render on screen
        return result;
    }

    @NotNull
    private View getGameView(int position, View convertView, ViewGroup parent, Game gameModel) {
        ViewHolder viewHolder;
        View result;
        if (convertView == null) {

            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.schedule_game_row, parent, false);
            viewHolder.gameDate = convertView.findViewById(R.id.scheduleGameDate);
            viewHolder.gameLocation = convertView.findViewById(R.id.scheduleGameLocation);
            viewHolder.homeTeam = convertView.findViewById(R.id.scheduleHomeTeam);
            viewHolder.awayTeam = convertView.findViewById(R.id.scheduleAwayTeam);
            viewHolder.vsScore = convertView.findViewById(R.id.vsScore);

            result = convertView;

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            result = convertView;
        }

        lastPosition = position;

        if (gameModel != null) {
            viewHolder.gameDate.setText(gameModel.getDateString());
            viewHolder.gameLocation.setText(gameModel.getLocation());
            viewHolder.homeTeam.setText(gameModel.getHomeFormatted(24));
            viewHolder.awayTeam.setText(gameModel.getAwayFormatted(24));

            if (!gameModel.isInFuture())
            {
                viewHolder.vsScore.setTypeface(null, Typeface.BOLD);
                viewHolder.vsScore.setText(String.format("%d - %d", gameModel.getHomeScore(), gameModel.getAwayScore()));
                Drawable scoreWidgit = getContext().getDrawable(R.drawable.score_widget);
                viewHolder.vsScore.setBackground(scoreWidgit);

                if (gameModel.getResult() != null) {
                    switch (gameModel.getResult()) {
                        case Win:
                            scoreWidgit.setTint(getContext().getResources().getColor(R.color.winColor));
                            break;
                        case Loss:
                            scoreWidgit.setTint(getContext().getResources().getColor(R.color.lossColor));
                            break;
                        default:
                            scoreWidgit.setTint(getContext().getResources().getColor(R.color.tieColor));
                            break;
                    }
                }
            }
        }
        return result;
    }
}

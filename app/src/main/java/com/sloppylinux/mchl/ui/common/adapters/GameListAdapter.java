package com.sloppylinux.mchl.ui.common.adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.sloppylinux.mchl.domain.Game;
import com.sloppylinux.mchl.ui.R;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GameListAdapter extends ArrayAdapter<Game> {

    private List<Game> gameList;
    Context mContext;

    // View lookup cache
    static class ViewHolder {
        @BindView(R.id.scheduleGameDate)
        TextView gameDate;
        @BindView(R.id.scheduleGameLocation)
        TextView gameLocation;
        @BindView(R.id.scheduleHomeTeam)
        TextView homeTeam;
        @BindView(R.id.scheduleAwayTeam)
        TextView awayTeam;
        @BindView(R.id.vsScore)
        TextView vsScore;

        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    public GameListAdapter(List<Game> data, Context context) {
        super(context, R.layout.schedule_game_row, data);
        this.gameList = data;
        this.mContext = context;
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

            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.schedule_game_row, parent, false);
            viewHolder = new ViewHolder(convertView);
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

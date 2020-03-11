package com.sloppylinux.mchl.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;
import com.sloppylinux.mchl.domain.Game;
import com.sloppylinux.mchl.ui.R;

import org.jetbrains.annotations.NotNull;

import java.util.Date;
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
        TextView homeScore;
        TextView awayScore;
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

        final View result;

        if (gameModel != null && gameModel.isInFuture()) {
            result = getScheduleView(position, convertView, parent, gameModel);
        } else {
            // null gameModel is handled in getResult view
            result = getResultView(position, convertView, parent, gameModel);
        }
        // Return the completed view to render on screen
        return result;
    }

    @NotNull
    private View getScheduleView(int position, View convertView, ViewGroup parent, Game gameModel) {
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
        }
        return result;
    }

    @NotNull
    private View getResultView(int position, View convertView, ViewGroup parent, Game gameModel) {
        ViewHolder viewHolder;
        View result;
        if (convertView == null) {

            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.result_game_row, parent, false);
            viewHolder.gameDate = convertView.findViewById(R.id.resultGameDate);
            viewHolder.homeTeam = convertView.findViewById(R.id.resultHomeTeam);
            viewHolder.awayTeam = convertView.findViewById(R.id.resultAwayTeam);
            viewHolder.homeScore = convertView.findViewById(R.id.resultHomeScore);
            viewHolder.awayScore = convertView.findViewById(R.id.resultAwayScore);

            result = convertView;

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            result = convertView;
        }

        lastPosition = position;

        if (gameModel != null) {
            viewHolder.gameDate.setText(gameModel.getDateString());
            viewHolder.homeTeam.setText(gameModel.getHome());
            viewHolder.awayTeam.setText(gameModel.getAway());

            if (gameModel.getDate().after(new Date())) {
                viewHolder.homeScore.setVisibility(View.GONE);
                viewHolder.awayScore.setVisibility(View.GONE);
            } else {
                viewHolder.homeScore.setVisibility(View.VISIBLE);
                viewHolder.awayScore.setVisibility(View.VISIBLE);
                viewHolder.homeScore.setText(String.format("%d", gameModel.getHomeScore()));
                viewHolder.awayScore.setText(String.format("%d", gameModel.getAwayScore()));
            }
        }
        return result;
    }
}

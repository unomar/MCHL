package com.sloppylinux.mchl.ui.common.adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import androidx.appcompat.content.res.AppCompatResources;
import androidx.viewbinding.ViewBinding;

import com.sloppylinux.mchl.domain.Game;
import com.sloppylinux.mchl.R;
import com.sloppylinux.mchl.databinding.ScheduleGameRowBinding;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.skia.impl.Log;

import java.util.List;


public class GameListAdapter extends ArrayAdapter<Game> {
//    private ViewBinding binding;
    private List<Game> gameList;
    Context mContext;

    // View lookup cache
    static class ViewHolder {
        ScheduleGameRowBinding binding;

        public ViewHolder(View view, ScheduleGameRowBinding binding) {
            this.binding = binding;
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
            ScheduleGameRowBinding binding = ScheduleGameRowBinding.inflate(inflater, parent, false);
            convertView = binding.getRoot();
            viewHolder = new ViewHolder(convertView, binding);

            result = convertView;

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            result = convertView;
        }

        lastPosition = position;

        if (gameModel != null) {
            viewHolder.binding.scheduleGameDate.setText(gameModel.getDateString());
            viewHolder.binding.scheduleGameLocation.setText(gameModel.getLocation());
            viewHolder.binding.scheduleHomeTeam.setText(gameModel.getHomeFormatted(24));
            viewHolder.binding.scheduleAwayTeam.setText(gameModel.getAwayFormatted(24));


            if (!gameModel.isInFuture()) {
                viewHolder.binding.vsScore.setTypeface(null, Typeface.BOLD);
                viewHolder.binding.vsScore.setText(String.format("%d - %d", gameModel.getHomeScore(), gameModel.getAwayScore()));
                Drawable scoreWidgit = AppCompatResources.getDrawable(getContext(), R.drawable.score_widget);
                viewHolder.binding.vsScore.setBackground(scoreWidgit);

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

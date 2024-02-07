package com.sloppylinux.mchl.ui.common.adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.sloppylinux.mchl.databinding.PlayerRowBinding;
import com.sloppylinux.mchl.domain.sportspress.PlayerStatistic;
import com.sloppylinux.mchl.R;

import java.util.List;


public class TeamPlayersListAdapter extends ArrayAdapter<PlayerStatistic>
{
    private int lastPosition = -1;

    public TeamPlayersListAdapter(List<PlayerStatistic> playerStatistics, Context context)
    {
        super(context, R.layout.player_row, playerStatistics);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        // Get the data item for this position
        PlayerStatistic playerModel = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        @NonNull PlayerRowBinding viewHolder; // view lookup cache stored in tag

        final View result;

        if (convertView == null)
        {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.player_row, parent, false);
            viewHolder = PlayerRowBinding.inflate(inflater);

            result = convertView;

            convertView.setTag(viewHolder);
        } else
        {
            viewHolder = (PlayerRowBinding) convertView.getTag();
            result = convertView;
        }

        lastPosition = position;

        if (!"Player".equals(playerModel.getName()))
        {
            viewHolder.playerRowName.setText(playerModel.getName());
            viewHolder.playerGamesPlayed.setText(String.valueOf(playerModel.getGamesPlayed()));
            viewHolder.playerGoals.setText(String.valueOf(playerModel.getGoals()));
            viewHolder.playerAssists.setText(String.valueOf(playerModel.getAssists()));
            viewHolder.playerPoints.setText(String.valueOf(playerModel.getPoints()));
            viewHolder.playerPims.setText(String.valueOf(playerModel.getPenaltyMins()));
        }
        else
        {
            setHeader(viewHolder.playerRowName,getContext().getString(R.string.Name));
            setHeader(viewHolder.playerGamesPlayed,getContext().getString(R.string.gamesPlayedHeader));
            setHeader(viewHolder.playerGoals,getContext().getString(R.string.Goals));
            setHeader(viewHolder.playerAssists,getContext().getString(R.string.Assists));
            setHeader(viewHolder.playerPoints,getContext().getString(R.string.Points));
            setHeader(viewHolder.playerPims,getContext().getString(R.string.pimHeader));
        }

        // Return the completed view to render on screen
        return result;
    }

    /**
     * Set Header attributes on the TextView
     * @param textView The TextView to configure
     * @param text The text to display
     */
    private void setHeader(TextView textView, String text)
    {
        textView.setText(text);
        textView.setTypeface(null, Typeface.BOLD);
    }
}

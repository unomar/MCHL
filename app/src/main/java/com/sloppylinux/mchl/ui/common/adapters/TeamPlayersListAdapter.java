package com.sloppylinux.mchl.ui.common.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.sloppylinux.mchl.domain.Player;
import com.sloppylinux.mchl.domain.Team;
import com.sloppylinux.mchl.ui.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TeamPlayersListAdapter extends ArrayAdapter<Player>
{

    private List<Player> playerList;
    Context mContext;

    // View lookup cache
    static class ViewHolder
    {
        @BindView(R.id.playerRowName)
        TextView playerName;
        @BindView(R.id.playerGamesPlayed)
        TextView gamesPlayed;
        @BindView(R.id.playerGoals)
        TextView goals;
        @BindView(R.id.playerAssists)
        TextView assists;
        @BindView(R.id.playerPoints)
        TextView points;
        @BindView(R.id.playerPims)
        TextView pims;

        public ViewHolder(View view)
        {
            ButterKnife.bind(this, view);
        }
    }

    public TeamPlayersListAdapter(Team team, Context context)
    {
        super(context, R.layout.player_row, team.getPlayers());
        this.playerList = team.getPlayers();
        this.mContext = context;
    }

    private int lastPosition = -1;

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        // Get the data item for this position
        Player playerModel = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder; // view lookup cache stored in tag

        final View result;

        if (convertView == null)
        {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.player_row, parent, false);
            viewHolder = new ViewHolder(convertView);

            result = convertView;

            convertView.setTag(viewHolder);
        } else
        {
            viewHolder = (ViewHolder) convertView.getTag();
            result = convertView;
        }

        lastPosition = position;

        viewHolder.playerName.setText(playerModel.getName());
        viewHolder.gamesPlayed.setText(String.valueOf(playerModel.getGamesPlayed()));
        viewHolder.goals.setText(String.valueOf(playerModel.getGoals()));
        viewHolder.assists.setText(String.valueOf(playerModel.getAssists()));
        viewHolder.points.setText(String.valueOf(playerModel.getPoints()));
        viewHolder.pims.setText(String.valueOf(playerModel.getPims()));

        // Return the completed view to render on screen
        return result;
    }
}

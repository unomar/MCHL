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
import com.sloppylinux.mchl.util.Utils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PlayerListAdapter extends ArrayAdapter<Player> implements View.OnClickListener{

    private List<Player> playerList;
    private String numberFormat = "#%d";
    Context mContext;

    // View lookup cache
    static class ViewHolder {
        @BindView(R.id.player_row_name)
        TextView playerName;
        @BindView(R.id.player_row_number)
        TextView playerNumber;
        @BindView(R.id.player_row_teams)
        TextView playerTeams;

        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    public PlayerListAdapter(List<Player> data, Context context) {
        super(context, R.layout.player_row, data);
        this.playerList = data;
        this.mContext = context;
    }

    @Override
    public void onClick(View v) {

        int position=(Integer) v.getTag();
        Object object= getItem(position);
        Player player = (Player) object;

        // Do Stuff
//        switch (v.getId())
//        {
//            case R.id.item_info:
//                Snackbar.make(v, "Release date " +dataModel.getFeature(), Snackbar.LENGTH_LONG)
//                        .setAction("No action", null).show();
//                break;
//        }
    }

    private int lastPosition = -1;

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Player playerModel = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder; // view lookup cache stored in tag

        final View result;

        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.player_lookup_row, parent, false);
            viewHolder = new ViewHolder(convertView);

            result=convertView;

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            result=convertView;
        }

        lastPosition = position;

        viewHolder.playerName.setText(playerModel.getName());
        Integer number = playerModel.getNumber();
        if (number != null) {
            viewHolder.playerNumber.setText(String.format(numberFormat, number));
        }

        String playerTeams = "";
        for (Team team : playerModel.getPlayerTeams())
        {
            playerTeams += Utils.convertString(team.getName());
        }
        viewHolder.playerTeams.setText(playerTeams);

        // Return the completed view to render on screen
        return result;
    }
}

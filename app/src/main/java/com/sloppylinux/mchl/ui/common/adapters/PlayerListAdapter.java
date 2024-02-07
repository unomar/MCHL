package com.sloppylinux.mchl.ui.common.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.sloppylinux.mchl.databinding.PlayerLookupRowBinding;
import com.sloppylinux.mchl.domain.Player;
import com.sloppylinux.mchl.domain.Team;
import com.sloppylinux.mchl.R;
import com.sloppylinux.mchl.util.Utils;

import java.util.List;

public class PlayerListAdapter extends ArrayAdapter<Player>{
    private PlayerLookupRowBinding binding;
    private List<Player> playerList;
    private String numberFormat = "#%d";
    Context mContext;

    // View lookup cache
    static class ViewHolder {

        public ViewHolder(View view) {
        }
    }

    public PlayerListAdapter(List<Player> data, PlayerLookupRowBinding playerRowBinding, Context context) {
        super(context, R.layout.player_row, data);
        binding = playerRowBinding;
        this.playerList = data;
        this.mContext = context;
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

        binding.playerRowName.setText(playerModel.getName());
        Integer number = playerModel.getNumber();
        if (number != null) {
            binding.playerRowNumber.setText(String.format(numberFormat, number));
        }

        String playerTeams = "";
        for (Team team : playerModel.getPlayerTeams())
        {
            playerTeams += Utils.convertString(team.getName());
        }
        binding.playerRowTeams.setText(playerTeams);

        // Return the completed view to render on screen
        return result;
    }
}

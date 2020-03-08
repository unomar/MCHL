package com.sloppylinux.mchl.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.sloppylinux.mchl.domain.Game;
import com.sloppylinux.mchl.ui.R;

import java.util.List;

public class GameListAdapter extends ArrayAdapter<Game> implements View.OnClickListener{

    private List<Game> gameList;
    Context mContext;

    // View lookup cache
    private static class ViewHolder {
        TextView gameDate;
        TextView gameLocation;
        TextView homeTeam;
        TextView awayTeam;
    }

    public GameListAdapter(List<Game> data, Context context) {
        super(context, R.layout.game_row, data);
        this.gameList = data;
        this.mContext = context;
    }

    @Override
    public void onClick(View v) {

        int position=(Integer) v.getTag();
        Object object= getItem(position);
        Game game = (Game) object;

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
        Game gameModel = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder; // view lookup cache stored in tag

        final View result;

        if (convertView == null) {

            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.game_row, parent, false);
            viewHolder.gameDate = convertView.findViewById(R.id.gameDate);
            viewHolder.gameLocation = convertView.findViewById(R.id.gameLocation);
            viewHolder.homeTeam = convertView.findViewById(R.id.homeTeam);
            viewHolder.awayTeam = convertView.findViewById(R.id.awayTeam);

            result=convertView;

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            result=convertView;
        }

//        Animation animation = AnimationUtils.loadAnimation(mContext, (position > lastPosition) ? R.anim.up_from_bottom : R.anim.down_from_top);
//        result.startAnimation(animation);
        lastPosition = position;

        viewHolder.gameDate.setText(gameModel.getDateString());
        viewHolder.gameLocation.setText(gameModel.getLocation());
        viewHolder.homeTeam.setText(gameModel.getHomeFormatted(24));
        viewHolder.awayTeam.setText(gameModel.getAwayFormatted(24));
        // Return the completed view to render on screen
        return result;
    }
}

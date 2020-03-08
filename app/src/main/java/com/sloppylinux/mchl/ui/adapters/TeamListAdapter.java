package com.sloppylinux.mchl.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.sloppylinux.mchl.domain.Game;
import com.sloppylinux.mchl.domain.Team;
import com.sloppylinux.mchl.ui.R;

import java.util.List;

public class TeamListAdapter extends ArrayAdapter<Team>{

    private List<Team> teamList;
    Context mContext;

    // View lookup cache
    private static class ViewHolder {
        TextView teamName;
        TextView teamRecord;
    }

    public TeamListAdapter(List<Team> data, Context context) {
        super(context, R.layout.homepage_team, data);
        this.teamList = data;
        this.mContext = context;
    }

    private int lastPosition = -1;

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Team teamModel = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder; // view lookup cache stored in tag

        final View result;

        if (convertView == null) {

            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.homepage_team, parent, false);
            viewHolder.teamName = convertView.findViewById(R.id.homepage_team_name);
            viewHolder.teamRecord = convertView.findViewById(R.id.homepage_team_record);

            result=convertView;

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            result=convertView;
        }

//        Animation animation = AnimationUtils.loadAnimation(mContext, (position > lastPosition) ? R.anim.up_from_bottom : R.anim.down_from_top);
//        result.startAnimation(animation);
        lastPosition = position;

        viewHolder.teamName.setText(teamModel.getName());
        viewHolder.teamRecord.setText(teamModel.getRecord());
        // Return the completed view to render on screen
        return result;
    }
}

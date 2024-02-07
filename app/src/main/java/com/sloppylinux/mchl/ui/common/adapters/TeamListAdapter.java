package com.sloppylinux.mchl.ui.common.adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.sloppylinux.mchl.databinding.HomepageTeamBinding;
import com.sloppylinux.mchl.domain.Team;
import com.sloppylinux.mchl.R;

import java.util.List;

public class TeamListAdapter extends ArrayAdapter<Team>{
    private List<Team> teamList;
    Context mContext;

    // View lookup cache
    static class ViewHolder {
        HomepageTeamBinding binding;

        public ViewHolder(View view) {

        }
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
            LayoutInflater inflater = LayoutInflater.from(getContext());
            HomepageTeamBinding homepageTeamBinding = HomepageTeamBinding.inflate(inflater, parent, false);

            convertView = homepageTeamBinding.getRoot();
            viewHolder = new ViewHolder(convertView);
            viewHolder.binding = homepageTeamBinding;

            result=convertView;

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            result=convertView;
        }

        lastPosition = position;

        // First entry should be our header
        if (position == 0)
        {
            viewHolder.binding.homepageTeamName.setText(getContext().getString(R.string.team_name));
            viewHolder.binding.homepageTeamWins.setText(getContext().getString(R.string.winHeader));
            viewHolder.binding.homepageTeamLosses.setText(getContext().getString(R.string.lossHeader));
            viewHolder.binding.homepageTeamTies.setText(getContext().getString(R.string.tieHeader));

            viewHolder.binding.homepageTeamName.setTypeface(null, Typeface.BOLD);
            viewHolder.binding.homepageTeamWins.setTypeface(null, Typeface.BOLD);
            viewHolder.binding.homepageTeamLosses.setTypeface(null, Typeface.BOLD);
            viewHolder.binding.homepageTeamTies.setTypeface(null, Typeface.BOLD);
        }
        else
        {
            viewHolder.binding.homepageTeamName.setText(teamModel.getNameFormatted(24));
            viewHolder.binding.homepageTeamWins.setText(String.valueOf(teamModel.getWins()));
            viewHolder.binding.homepageTeamLosses.setText(String.valueOf(teamModel.getLosses()));
            viewHolder.binding.homepageTeamTies.setText(String.valueOf(teamModel.getTies()));
        }
        // Return the completed view to render on screen
        return result;
    }

    @Override
    public Team getItem(int position)
    {
        // Inject a null object for a header
        if (position == 0)
        {
            return null;
        }
        return teamList.get(position - 1);
    }

    @Override
    public int getCount()
    {
        // Return 1 greater to generate a header
        return teamList.size() + 1;
    }
}

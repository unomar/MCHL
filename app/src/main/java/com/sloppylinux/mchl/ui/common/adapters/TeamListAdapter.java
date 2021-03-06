package com.sloppylinux.mchl.ui.common.adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.sloppylinux.mchl.domain.Team;
import com.sloppylinux.mchl.ui.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TeamListAdapter extends ArrayAdapter<Team>{

    private List<Team> teamList;
    Context mContext;

    // View lookup cache
    static class ViewHolder {
        @BindView(R.id.homepage_team_name)
        TextView teamName;
        @BindView(R.id.homepage_team_wins)
        TextView wins;
        @BindView(R.id.homepage_team_losses)
        TextView losses;
        @BindView(R.id.homepage_team_ties)
        TextView ties;

        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
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
            convertView = inflater.inflate(R.layout.homepage_team, parent, false);
            viewHolder = new ViewHolder(convertView);

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
            viewHolder.teamName.setText(getContext().getString(R.string.team_name));
            viewHolder.wins.setText(getContext().getString(R.string.winHeader));
            viewHolder.losses.setText(getContext().getString(R.string.lossHeader));
            viewHolder.ties.setText(getContext().getString(R.string.tieHeader));

            viewHolder.teamName.setTypeface(null, Typeface.BOLD);
            viewHolder.wins.setTypeface(null, Typeface.BOLD);
            viewHolder.losses.setTypeface(null, Typeface.BOLD);
            viewHolder.ties.setTypeface(null, Typeface.BOLD);
        }
        else
        {
            viewHolder.teamName.setText(teamModel.getName());
            viewHolder.wins.setText(String.valueOf(teamModel.getWins()));
            viewHolder.losses.setText(String.valueOf(teamModel.getLosses()));
            viewHolder.ties.setText(String.valueOf(teamModel.getTies()));
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

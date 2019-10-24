package com.sloppylinux.mchl.gui;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TextView;

import com.sloppylinux.mchl.domain.Division;
import com.sloppylinux.mchl.domain.Team;

public class StandingsTab extends Activity
{
    private static String tag = "StandingsTab";
    private LayoutInflater li;
    private Division division;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        Bundle extras = getIntent().getExtras();
        if (extras == null)
        {
            Log.e(tag, "No division found.  Cannot display standings");
            finish();
        }
        this.division = (Division) extras.getSerializable("Division");

        li = this.getLayoutInflater();

        setContentView(R.layout.standings_tab);

        createDivisionView(division);
    }

    private void createDivisionView(Division division)
    {
        TableLayout divTable = (TableLayout) findViewById(R.id.StandingsTab);

        for (Team team : division.getTeams())
        {
            divTable.addView(createTeamRow(team));
        }
    }

    private View createTeamRow(Team team)
    {
        View header = li.inflate(R.layout.team_row, null);

        TextView teamName = (TextView) header.findViewById(R.id.teamName);
        teamName.setText(team.getName());
        TextView gamesPlayed = (TextView) header.findViewById(R.id.gamesPlayed);
        gamesPlayed.setText(Integer.toString(team.getGamesPlayed()));
        TextView wins = (TextView) header.findViewById(R.id.wins);
        wins.setText(Integer.toString(team.getWins()));
        TextView losses = (TextView) header.findViewById(R.id.losses);
        losses.setText(Integer.toString(team.getLosses()));
        TextView ties = (TextView) header.findViewById(R.id.ties);
        ties.setText(Integer.toString(team.getTies()));
        TextView goalsFor = (TextView) header.findViewById(R.id.goalsFor);
        goalsFor.setText(Integer.toString(team.getGoalsFor()));
        TextView goalsAgainst = (TextView) header.findViewById(R.id.goalsAgainst);
        goalsAgainst.setText(Integer.toString(team.getGoalsAgainst()));
        TextView points = (TextView) header.findViewById(R.id.points);
        points.setText(Integer.toString(team.getPoints()));

        return header;
    }
}

package com.sloppylinux.mchl.gui;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.sloppylinux.mchl.domain.Goalie;
import com.sloppylinux.mchl.domain.Player;
import com.sloppylinux.mchl.domain.Team;

import java.util.Collections;
import java.util.List;

public class TeamStatsTab extends Activity
{
    private static String tag = "TeamStatsTab";
    private LayoutInflater li;
    private Team team;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        Bundle extras = getIntent().getExtras();
        if (extras == null)
        {
            Log.e(tag, "No team found.  Cannot display stats");
            finish();
        }
        this.team = (Team) extras.getSerializable("Team");

        li = this.getLayoutInflater();

        setContentView(R.layout.team_players);

        createTeamView(team);
    }

    private void createTeamView(Team team)
    {
        TextView teamName = (TextView) findViewById(R.id.teamName);
        teamName.setText(team.getName());

        TableRow goalieRow = (TableRow) findViewById(R.id.goalieRow);
        buildGoalieStats(goalieRow, team.getGoalie());

        TableLayout players = (TableLayout) findViewById(R.id.playerTable);

        List<Player> playerList = team.getPlayers();
        Collections.sort(playerList);

        for (Player player : playerList)
        {
            players.addView(createPlayerRow(player));
        }
    }

    private void buildGoalieStats(View goalieRow, Goalie goalie)
    {
        TextView name = (TextView) goalieRow.findViewById(R.id.nameCol);
        name.setText(goalie.getName(" "));
        TextView gamesPlayed = (TextView) goalieRow.findViewById(R.id.gpCol);
        gamesPlayed.setText(Integer.toString(goalie.getGamesPlayed()));
        TextView goalsAgainst = (TextView) goalieRow.findViewById(R.id.gaCol);
        goalsAgainst.setText(Integer.toString(goalie.getGoalsAgainst()));
        TextView shots = (TextView) goalieRow.findViewById(R.id.sCol);
        shots.setText(Integer.toString(goalie.getShots()));
        TextView gAA = (TextView) goalieRow.findViewById(R.id.gaaCol);
        gAA.setText(Float.toString(goalie.getgAA()));
        TextView savePercentage = (TextView) goalieRow.findViewById(R.id.spCol);
        savePercentage.setText(Float.toString(goalie.getSavePercentage()));
    }

    private View createPlayerRow(Player player)
    {
        View playerRow = li.inflate(R.layout.player_row, null);

        TextView name = (TextView) playerRow.findViewById(R.id.nameCol);
        name.setText(player.getName(" "));
        TextView gamesPlayed = (TextView) playerRow.findViewById(R.id.gamesPlayedCol);
        gamesPlayed.setText(Integer.toString(player.getGamesPlayed()));
        TextView goals = (TextView) playerRow.findViewById(R.id.goalsCol);
        goals.setText(Integer.toString(player.getGoals()));
        TextView assists = (TextView) playerRow.findViewById(R.id.assistsCol);
        assists.setText(Integer.toString(player.getAssists()));
        TextView points = (TextView) playerRow.findViewById(R.id.pointsCol);
        points.setText(Integer.toString(player.getPoints()));
        TextView pims = (TextView) playerRow.findViewById(R.id.pimsCol);
        pims.setText(Integer.toString(player.getPims()));

        return playerRow;
    }
}

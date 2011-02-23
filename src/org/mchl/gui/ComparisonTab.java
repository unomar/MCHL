package org.mchl.gui;

import org.mchl.domain.Team;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class ComparisonTab extends Activity 
{
	private static String tag = "ComparisonTab";
	private Team home;
	private Team away;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		
		Bundle extras = getIntent().getExtras();
		if (extras == null)
		{
			Log.e(tag, "No teams found.  Cannot display stats");
			finish();
		}
		this.home = (Team) extras.getSerializable("Home");
		this.away = (Team) extras.getSerializable("Away");
		
		setContentView(R.layout.comparison);
		
		createComparisonView();
	}
	
	private void createComparisonView()
	{
		
		populateHome();
		populateAway();
		
		
	}
	
	private void populateHome()
	{
		// Populate home team data
		TextView rank = (TextView)findViewById(R.id.homeRank);
		rank.setText(Integer.toString(home.getRank()));
		
		TextView gamesPlayed = (TextView)findViewById(R.id.homeGP);
		gamesPlayed.setText(Integer.toString(home.getGamesPlayed()));
		
		TextView wins = (TextView)findViewById(R.id.homeWins);
		wins.setText(Integer.toString(home.getWins()));
		
		TextView losses = (TextView)findViewById(R.id.homeLosses);
		losses.setText(Integer.toString(home.getLosses()));

		TextView ties = (TextView)findViewById(R.id.homeTies);
		ties.setText(Integer.toString(home.getTies()));
		
		TextView points = (TextView)findViewById(R.id.homePoints);
		points.setText(Integer.toString(home.getPoints()));
		
		TextView goalsFor = (TextView)findViewById(R.id.homeGF);
		goalsFor.setText(Integer.toString(home.getGoalsFor()));
		
		TextView goalsAgainst = (TextView)findViewById(R.id.homeGA);
		goalsAgainst.setText(Integer.toString(home.getGoalsAgainst()));
	}
	
	private void populateAway()
	{
		// Populate away team data
		TextView rank = (TextView)findViewById(R.id.awayRank);
		rank.setText(Integer.toString(away.getRank()));
		
		TextView gamesPlayed = (TextView)findViewById(R.id.awayGP);
		gamesPlayed.setText(Integer.toString(away.getGamesPlayed()));
		
		TextView wins = (TextView)findViewById(R.id.awayWins);
		wins.setText(Integer.toString(away.getWins()));
		
		TextView losses = (TextView)findViewById(R.id.awayLosses);
		losses.setText(Integer.toString(away.getLosses()));

		TextView ties = (TextView)findViewById(R.id.awayTies);
		ties.setText(Integer.toString(away.getTies()));
		
		TextView points = (TextView)findViewById(R.id.awayPoints);
		points.setText(Integer.toString(away.getPoints()));
		
		TextView goalsFor = (TextView)findViewById(R.id.awayGF);
		goalsFor.setText(Integer.toString(away.getGoalsFor()));
		
		TextView goalsAgainst = (TextView)findViewById(R.id.awayGA);
		goalsAgainst.setText(Integer.toString(away.getGoalsAgainst()));
	}
	
}

package com.sloppylinux.mchl.gui;

import com.sloppylinux.mchl.domain.Team;
import com.sloppylinux.mchl.util.Config;
import com.sloppylinux.mchl.util.MCHLWebservice;
import com.sloppylinux.mchl.domain.Game;
import com.sloppylinux.mchl.domain.WSException;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.app.TabActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TabHost;
import android.widget.TextView;

public class Matchup extends TabActivity
{
	private Game game;
	private String tag = "Matchup";
	private Config config;
	private ProgressDialog pd = null;
	private static Matchup me;
	private TabHost matchupTabs = null;

	private static Team home;
	private static Team away;

	final Handler mHandler = new Handler();

	final Runnable mUpdateResults = new Runnable()
	{
		public void run()
		{
			buildDisplay();
		}
	};

	final Runnable displayError = new Runnable()
	{
		public void run()
		{
			displayError();
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		me = this;
		Bundle extras = getIntent().getExtras();
		if (extras == null)
		{
			Log.e(tag, "No game found.  Cannot display matchup");
			finish();
		}
		this.game = (Game) extras.getSerializable("Game");
		this.config = new Config(this);

		setContentView(R.layout.matchup_view);

		fetchWebData(false);
	}

	protected void fetchWebData(final boolean forceRefresh)
	{
		pd = ProgressDialog.show(this, "Please wait...", "Fetching data");
		Thread t = new Thread()
		{
			public void run()
			{
				Looper.prepare();

				// Get Teams
				try
				{
					home = MCHLWebservice.getTeam(config.getSeason(), config
							.getDivision(), game.getHome(), me, forceRefresh);
					away = MCHLWebservice.getTeam(config.getSeason(), config
							.getDivision(), game.getAway(), me, forceRefresh);

					mHandler.post(mUpdateResults);
				}
				catch (WSException e)
				{
					mHandler.post(displayError);
				}

			}
		};
		t.start();
	}

	private void displayError()
	{
		Log.e(tag, "Caught exception getting matchup info");
		pd.dismiss();
		pd = null;
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage("Connection to server failed.").setCancelable(false)
				.setPositiveButton("Ok", new DialogInterface.OnClickListener()
				{
					public void onClick(DialogInterface dialog, int id)
					{
						me.finish();
					}
				});
		AlertDialog alert = builder.create();
		alert.show();
	}

	private void buildDisplay()
	{
		if (matchupTabs != null)
		{
			matchupTabs.getTabWidget().removeAllViews();
		}
		if (pd != null && pd.isShowing())
		{
			pd.dismiss();
			pd = null;
		}

		// Insert Date & Location info
		TextView date = (TextView) findViewById(R.id.date);
		date.setText(this.game.getDateString());
		TextView loc = (TextView) findViewById(R.id.location);
		loc.setText(this.game.getShortLocation());

		// Insert Team name & Score info
		TextView homeTeamName = (TextView) findViewById(R.id.matchHomeName);
		homeTeamName.setText(home.getName());
		TextView awayTeamName = (TextView) findViewById(R.id.matchAwayName);
		awayTeamName.setText(away.getName());

		if ((this.game.getHomeScore() != -1)
				&& (this.game.getAwayScore() != -1))
		{
			TextView homeTeamScore = (TextView) findViewById(R.id.homeTeamScore);
			homeTeamScore.setText(Integer.toString(this.game.getHomeScore()));
			TextView awayTeamScore = (TextView) findViewById(R.id.awayTeamScore);
			awayTeamScore.setText(Integer.toString(this.game.getAwayScore()));
		}

		matchupTabs = getTabHost();
		TabHost.TabSpec spec; // Resusable TabSpec for each tab
		Intent intent; // Reusable Intent for each tab

		// Build Home Team stats tab
		intent = new Intent().setClass(this, TeamStatsTab.class);
		intent.putExtra("Team", home);
		spec = matchupTabs.newTabSpec("Home").setIndicator("Home").setContent(
				intent);
		matchupTabs.addTab(spec);

		// Build Comparison tab
		intent = new Intent().setClass(this, ComparisonTab.class);
		intent.putExtra("Home", home);
		intent.putExtra("Away", away);
		spec = matchupTabs.newTabSpec("VS").setIndicator("VS").setContent(
				intent);
		matchupTabs.addTab(spec);

		// Build Away Team stats tab
		intent = new Intent().setClass(this, TeamStatsTab.class);
		intent.putExtra("Team", away);
		spec = matchupTabs.newTabSpec("Away").setIndicator("Away").setContent(
				intent);
		matchupTabs.addTab(spec);

		// Set comparison as the default tab
		matchupTabs.setCurrentTab(1);
	}

	/**
	 * Overrides the Activity options menu
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.layout.option_menu, menu);
		return super.onCreateOptionsMenu(menu);
	}

	/**
	 * Overrides the Activity option menu selected
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		switch (item.getItemId())
		{
		case R.id.refresh:
			fetchWebData(true);
			break;
		case R.id.configure:
			Intent config = new Intent(me, ConfigGUI.class);
			me.startActivity(config);
			fetchWebData(true);
			break;
		}

		return super.onOptionsItemSelected(item);
	}
}

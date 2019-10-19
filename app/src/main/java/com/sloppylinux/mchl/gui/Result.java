package com.sloppylinux.mchl.gui;

import java.util.Date;

import com.sloppylinux.mchl.util.Config;
import com.sloppylinux.mchl.util.MCHLWebservice;
import com.sloppylinux.mchl.domain.Game;
import com.sloppylinux.mchl.domain.TeamSchedule;
import com.sloppylinux.mchl.domain.WSException;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

public class Result extends Activity
{
	private TeamSchedule results;
	private static String tag = "Schedule";

	private ProgressDialog pd = null;
	private LayoutInflater li;
	private Config config;
	private static Result me;
	private ScrollView content = null;
	private View nextGame = null;

	final Handler mHandler = new Handler();

	final Runnable mUpdateResults = new Runnable()
	{
		public void run()
		{
			buildSchedule();
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
		config = new Config(this);
		me = this;

		content = new ScrollView(this);
		setContentView(content);

		li = this.getLayoutInflater();
		fetchWebData(false);
	}

	protected void fetchWebData(final boolean force)
	{
		pd = ProgressDialog.show(this, "Please wait...", "Fetching data");
		Thread t = new Thread()
		{
			public void run()
			{
				Looper.prepare();
				try
				{
					results = MCHLWebservice.getSchedule(config.getSeason(), config
							.getTeam(), me, force);
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
		Log.e(tag, "Caught exception getting schedule info");
		pd.dismiss();
		pd = null;
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage("Connection to server failed.")
		       .setCancelable(false)
		       .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
		           public void onClick(DialogInterface dialog, int id) {
		                me.finish();
		           }
		       });
		AlertDialog alert = builder.create();
		alert.show();
	}

	private void buildSchedule()
	{
		if (content != null)
		{
			content.removeAllViews();
		}
		if (pd != null && pd.isShowing())
		{
			pd.dismiss();
			pd = null;
		}

		LinearLayout gameSchedule = new LinearLayout(this);
		gameSchedule.setOrientation(LinearLayout.VERTICAL);

		Date now = new Date();
		for (Game game : results.getGames())
		{
			View gameView = createGameView(game);
			gameSchedule.addView(gameView);

			if ((nextGame == null)
					&& (game.getDate().getTime() > now.getTime()))
			{
				nextGame = gameView;
			}
		}

		content.addView(gameSchedule);
		content.post(new Runnable()
		{
			@Override
			public void run()
			{
				int scrollOffset = getScrollY();
				content.scrollTo(0, scrollOffset);
				content.requestLayout();
			}
		});
	}

	private View createGameView(Game game)
	{
		View gameView = li.inflate(R.layout.result_row, null);
		View ResultTable = gameView.findViewById(R.id.ResultTable);
		TextView date = (TextView) gameView.findViewById(R.id.date);
		TextView loc = (TextView) gameView.findViewById(R.id.location);
		TextView homeTeam = (TextView) gameView.findViewById(R.id.homeTeam);
		TextView homeScore = (TextView) gameView.findViewById(R.id.homeScore);
		TextView awayTeam = (TextView) gameView.findViewById(R.id.awayTeam);
		TextView awayScore = (TextView) gameView.findViewById(R.id.awayScore);

		date.setText(game.getDateString());
		loc.setText(game.getShortLocation());
		homeTeam.setText(game.getHome());
		awayTeam.setText(game.getAway());
		if (game.getHomeScore() != -1)
		{
			homeScore.setText(Integer.toString(game.getHomeScore()));
			awayScore.setText(Integer.toString(game.getAwayScore()));
		}

		// Set the tag on the ResultTable since it contains the onClick method
		ResultTable.setTag(game);

		return gameView;
	}

	private static void displaySelectedMatchup(Game game)
	{
		Intent match = new Intent(me, Matchup.class);
		match.putExtra("Game", game);
		me.startActivity(match);
	}

	private int getScrollY()
	{
		int pos = 1;
		if (nextGame != null)
		{
			pos = nextGame.getTop();
			if (pos > 5)
			{
				pos = pos - 5;
			}
		}
		return pos;
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
			this.config = this.config.reload(this);
			fetchWebData(true);
			break;
		}

		return super.onOptionsItemSelected(item);
	}
	
	/**
	 * Public method for use in onClick tag in xml
	 * @param v The view being clicked
	 */
	public void onClick(View v)
	{
		// Display matchup
		Game game = (Game) v.getTag();
		displaySelectedMatchup(game);
	}
}

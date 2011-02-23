package org.mchl.gui;

import org.mchl.domain.WSException;
import org.mchl.util.Config;
import org.mchl.util.MCHLWebservice;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

public class ConfigGUI extends Activity
{
    private static Config config;
    private static ConfigGUI me;
    
    private static TextView seasonValue;
    private static TextView divisionValue;
    private static TextView teamValue;
    
    final String tag = "ConfigGUI2";
    
    private LayoutInflater li;
    private ProgressDialog pd = null;

    final Handler mHandler = new Handler();

    final Runnable mUpdateResults = new Runnable()
    {
        public void run()
        {
            updateUI();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        me = this;
        config = new Config(this);
        
        li = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        pd = ProgressDialog.show(this, "Please wait...", "Fetching data");
        fetchWebData(); 
        // assemble UI Shell here
    }

    protected void fetchWebData()
    {
        Thread t = new Thread()
        {
            public void run()
            {
            	Looper.prepare();
                MCHLWebservice.init();
                mHandler.post(mUpdateResults);
            }
        };
        t.start();
    }

    private void updateUI()
    {
    	if (pd != null && pd.isShowing())
		{
			pd.dismiss();
			pd = null;
		}
    	ScrollView content = new ScrollView(this);
    	
    	//View configGUI = li.inflate(R.layout.config_gui, null);
    	LinearLayout configGUI = new LinearLayout(this);
    	configGUI.setOrientation(LinearLayout.VERTICAL);
    	
    	View season = li.inflate(R.layout.custom_spinner, null);
    	View division = li.inflate(R.layout.custom_spinner, null);
    	View team = li.inflate(R.layout.custom_spinner, null);
    	
    	TextView seasonLabel = (TextView)season.findViewById(R.id.selectorLabel);
    	seasonValue = (TextView)season.findViewById(R.id.selectorValue);
    	seasonLabel.setText("Season:");
    	if (config.isLoaded() && !"".equals(config.getSeason()))
    	{
    		seasonValue.setText(config.getSeason());
    	}
    	else
    	{
    		seasonValue.setText(R.string.SeasonTextDefault);
    	}
    	
    	TextView divisionLabel = (TextView)division.findViewById(R.id.selectorLabel);
    	divisionValue = (TextView)division.findViewById(R.id.selectorValue);
    	divisionLabel.setText("Division:");
    	if (config.isLoaded() && !"".equals(config.getDivision()))
    	{
    		divisionValue.setText(config.getDivision());
    	}
    	else
    	{
    		divisionValue.setText(R.string.DivisionTextDefault);
    	}
    	
    	TextView teamLabel = (TextView)team.findViewById(R.id.selectorLabel);
    	teamValue = (TextView)team.findViewById(R.id.selectorValue);
    	teamLabel.setText("Team:");
    	if (config.isLoaded() && !"".equals(config.getTeam()))
    	{
    		teamValue.setText(config.getTeam());
    	}
    	else
    	{
    		teamValue.setText(R.string.TeamTextDefault);
    	}
    	
    	configGUI.addView(season);
    	configGUI.addView(division);
    	configGUI.addView(team);
    	
    	content.addView(configGUI);
    	
        

        season.setOnClickListener(new OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                displaySeasonSelector();
            }
        });

        division.setOnClickListener(new OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                displayDivisionSelector();
            }
        });

        team.setOnClickListener(new OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                displayTeamSelector();
            }
        });
        setContentView(content);
    }

    private void displaySeasonSelector()
    {
    	
		try
		{
			final String[] seasons = MCHLWebservice.getSeasons();
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
	    	builder.setTitle("Select a season");
	    	builder.setItems(seasons, new DialogInterface.OnClickListener() 
	    	{
	    	    public void onClick(DialogInterface dialog, int item) 
	    	    {
	    	    	String season = seasons[item];
	    	    	seasonValue.setText(season);
	    	    	divisionValue.setText(R.string.DivisionTextDefault);
	    	    	teamValue.setText(R.string.TeamTextDefault);
	    	    	config.setSeason(season);
	    	    	config.storeValues();
	    	    }
	    	});
	    	AlertDialog alert = builder.create();
	    	alert.show();
		}
		catch (WSException e)
		{
			new AlertDialog.Builder(me)
		      .setMessage("Could not connect to server")
		      .setPositiveButton("Ok", null)
		      .setOnCancelListener(new OnCancelListener() {
		        public void onCancel(DialogInterface dialog) {
		          me.finish();
		        }}).show();
		}
    	
    }

    private void displayDivisionSelector()
    {
    	final String[] divisions = MCHLWebservice.getDivisionNames(config.getSeason());
    	
    	AlertDialog.Builder builder = new AlertDialog.Builder(this);
    	builder.setTitle("Select a division");
    	builder.setItems(divisions, new DialogInterface.OnClickListener() 
    	{
    	    public void onClick(DialogInterface dialog, int item) 
    	    {
    	    	String division = divisions[item];
    	    	divisionValue.setText(division);
    	    	teamValue.setText(R.string.TeamTextDefault);
    	    	config.setDivision(division);
    	    	config.storeValues();
    	    }
    	});
    	AlertDialog alert = builder.create();
    	alert.show();
    }

    private void displayTeamSelector()
    {
		try
		{
			final String[] teams = MCHLWebservice.getTeamNames(config.getSeason(), config.getDivision());
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
	    	builder.setTitle("Select a team");
	    	builder.setItems(teams, new DialogInterface.OnClickListener() 
	    	{
	    	    public void onClick(DialogInterface dialog, int item) 
	    	    {
	    	    	String team = teams[item];
	    	    	teamValue.setText(team);
	    	    	config.setTeam(team);
	    	    	config.storeValues();
	    	    }
	    	});
	    	AlertDialog alert = builder.create();
	    	alert.show();
		}
		catch (WSException e)
		{
			new AlertDialog.Builder(me)
		      .setMessage("Could not connect to server")
		      .setPositiveButton("Ok", null)
		      .setOnCancelListener(new OnCancelListener() {
		        public void onCancel(DialogInterface dialog) {
		          me.finish();
		        }}).show();
		}
    	
    }
}

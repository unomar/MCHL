package org.mchl.gui;

import org.mchl.util.Config;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

public class Omchl extends Activity
{
	//private Menu menu;
    private Config config;
	private static ProgressDialog pd;
	public static Context context;
	private static String tag = "Omchl";
	private static View menu;
	private LayoutInflater li;
	
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		context = this;
    	
		li = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		
		menu = li.inflate(R.layout.menu, null);
		setContentView(menu);
	
        this.config = new Config(this);

        if (config.isLoaded())
		{
            showMenu();
		}
		else // Prompt for values and save them.
		{
            showConfig(null);
		}
	}

    public void showConfig(View v)
    {
        Intent intent = new Intent(this,
                org.mchl.gui.ConfigGUI.class);

        startActivity(intent);
    }
    
    public void showStandings(View v)
    {
    	v.refreshDrawableState();
    	Intent intent = new Intent(this,
    			org.mchl.gui.Standings.class);
    	startActivity(intent);
    }

    public void showSchedule(View v)
    {
    	v.refreshDrawableState();
    	Intent intent = new Intent(this,
    			org.mchl.gui.Result.class);
    	startActivity(intent);
    }
    
    public void exit(View v)
    {
    	this.finish();
    }
    
    @Override
    public void onBackPressed() 
    {
    	// do something on back.
    	this.finish();
    }
    
    protected void showMenu()
    {
    	if (menu == null)
    	{
    		menu = li.inflate(R.layout.menu, null);
    	}
    	setContentView(menu);
    }
    
    public static void showDialog(String message)
    {
    	if (pd != null)
    	{
    		Omchl.dismissDialog();
    	}
    	Log.d(tag, "Opening dialog");
    	pd = ProgressDialog.show(context, "Please wait...", message, true, false);
    }
    
    public static void dismissDialog()
    {
    	Log.d(tag, "Closing dialog");
    	if (pd != null)
    	{
    		pd.dismiss();
    	}
    	pd = null;
    }
}

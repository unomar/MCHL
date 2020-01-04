package com.sloppylinux.mchl.gui;

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

import com.sloppylinux.mchl.domain.Division;
import com.sloppylinux.mchl.domain.WSException;
import com.sloppylinux.mchl.util.Config;
import com.sloppylinux.mchl.util.MCHLWebservice;

import java.util.ArrayList;

public class Standings extends TabActivity
{
    private static String tag = "Standings";
    private static Standings me;
    final Handler mHandler = new Handler();
    private ArrayList<Division> divisions;
    private ProgressDialog pd = null;
    final Runnable displayError = new Runnable()
    {
        public void run()
        {
            displayError();
        }
    };
    private Config config;
    private TabHost standings = null;
    final Runnable mUpdateResults = new Runnable()
    {
        public void run()
        {
            buildStandings();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        me = this;
        config = new Config(this);

        setContentView(R.layout.standings);

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
                    divisions = MCHLWebservice.getDivisions(config.getSeason(), me, force);
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
        Log.e(tag, "Caught exception getting standings info");
        pd.dismiss();
        pd = null;
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Connection to server failed.")
                .setCancelable(false)
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

    private void buildStandings()
    {
        if (standings != null)
        {
            standings.getTabWidget().removeAllViews();
        }
        if (pd != null && pd.isShowing())
        {
            pd.dismiss();
            pd = null;
        }

        standings = getTabHost();
        TabHost.TabSpec spec;  // Resusable TabSpec for each tab
        Intent intent;  // Reusable Intent for each tab
        int currentTab = 0;

        for (int i = 0; i < divisions.size(); i++)
        {
            Division d = divisions.get(i);

            // Create an Intent to launch an Activity for the tab (to be reused)
            intent = new Intent().setClass(this, StandingsTab.class);
            intent.putExtra("Division", d);

            spec = standings.newTabSpec(d.getName()).setIndicator(d.getName())
                    .setContent(intent);
            standings.addTab(spec);
            if (d.getName().equals(config.getDivision()))
            {
                currentTab = i;
            }
        }

        standings.setCurrentTab(currentTab);
    }

    /**
     * Overrides the Activity options menu
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.activity_navigation_drawer, menu);
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
                return true;
            case R.id.configure:
                Intent config = new Intent(me, ConfigGUI.class);
                me.startActivity(config);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}

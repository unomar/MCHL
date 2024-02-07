package com.sloppylinux.mchl.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.sloppylinux.mchl.databinding.ActivityNavigationBinding;
import com.sloppylinux.mchl.databinding.AppBarNavigationBinding;
import com.sloppylinux.mchl.domain.Player;
import com.sloppylinux.mchl.domain.Team;
import com.sloppylinux.mchl.R;
import com.sloppylinux.mchl.util.Config;

import org.apache.log4j.Logger;

public class MchlNavigation extends AppCompatActivity
{
    private ActivityNavigationBinding activityNavigationBinding;
    private AppBarNavigationBinding appBarBinding;

    DrawerLayout drawer;

//    NavigationView navigationView;
    private NavController navController;

    Toolbar toolbar;
    private AppBarConfiguration mAppBarConfiguration;
    private Config config;
    private Player player;
    private Logger logger = Logger.getLogger(MchlNavigation.class.getName());

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        logger.debug("Entering MCHL Navigation");
        super.onCreate(savedInstanceState);

        activityNavigationBinding = ActivityNavigationBinding.inflate(getLayoutInflater());
        appBarBinding = AppBarNavigationBinding.inflate(getLayoutInflater());

        setContentView(activityNavigationBinding.getRoot());

//        navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
        navController = navHostFragment.getNavController();

        drawer = activityNavigationBinding.drawerLayout;
        toolbar = appBarBinding.toolbar;
        setSupportActionBar(toolbar);

        NavigationUI.setupActionBarWithNavController(this, navController);





        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_schedule, R.id.nav_standings, R.id.nav_results,
                R.id.nav_settings)
                .setDrawerLayout(drawer)
                .build();
//        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
//        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
//        NavigationUI.setupWithNavController(activityNavigationBinding.navView, navController);

        config = new Config(this.getBaseContext());

        if (config.isLoaded())
        {
            player = config.getPlayer();

            if (player == null)
            {
                Intent settings = new Intent(this.getBaseContext(), InitialSetup.class);
                startActivity(settings);
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.navigation, menu);

        final TextView playerNameView = findViewById(R.id.playerName);
        final TextView playerDivView = findViewById(R.id.playerDivision);

        if (player != null)
        {
            playerNameView.setText(player.getShortInfo());
            StringBuilder sb = new StringBuilder();
            String sep = "";

            // TODO: Convert this to a dropdown or other multi-choice selector
            for (Team team : player.getPlayerTeams())
            {
                sb.append(sep);
                sb.append(team.getName());
                sep = ",";
            }
            playerDivView.setText(sb.toString());
        }
        return true;
    }

//    @Override
//    public boolean onSupportNavigateUp()
//    {
//        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
//        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
//                || super.onSupportNavigateUp();
//    }
    @Override
    public boolean onSupportNavigateUp() {
        return NavigationUI.navigateUp(navController, (DrawerLayout) null);
    }
}

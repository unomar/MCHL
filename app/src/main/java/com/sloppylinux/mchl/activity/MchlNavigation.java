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
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;
import com.sloppylinux.mchl.domain.Player;
import com.sloppylinux.mchl.domain.Team;
import com.sloppylinux.mchl.ui.R;
import com.sloppylinux.mchl.util.Config;

import org.apache.log4j.Logger;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MchlNavigation extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private Config config;
    private Player player;
    private Logger logger = Logger.getLogger(MchlNavigation.class.getName());

    @BindView(R.id.drawer_layout)
    DrawerLayout drawer;
    @BindView(R.id.nav_view)
    NavigationView navigationView;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        logger.debug("Entering MCHL Navigation");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);

        ButterKnife.bind(this);

        setSupportActionBar(toolbar);

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_schedule, R.id.nav_standings, R.id.nav_results,
                R.id.nav_settings)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

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
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.navigation, menu);

        final TextView playerNameView = findViewById(R.id.playerName);
        final TextView playerDivView = findViewById(R.id.playerDivision);

        if (player != null) {
            playerNameView.setText(player.getShortInfo());
            StringBuilder sb = new StringBuilder();
            String sep = "";
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

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
}

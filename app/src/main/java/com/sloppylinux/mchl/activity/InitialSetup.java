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

import java.util.Date;

public class InitialSetup extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;

    private Config config;

    private Player player;

    private Logger logger = Logger.getLogger(InitialSetup.class.getName());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        logger.debug("Entering initial setup");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup);
        //Toolbar toolbar = findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);
        //DrawerLayout drawer = findViewById(R.id.drawer_layout);
        //NavigationView navigationView = findViewById(R.id.nav_view);



        config = new Config(this.getBaseContext());

        if (config.isLoaded())
        {
            player = config.getPlayer();

            if (player == null)
            {
                // TODO: Create separate Activity for initial setup
//                Intent settings = new Intent(this.getBaseContext(), InitialSetup.class);
//                startActivity(settings);
            }
            else if (player.requiresUpdate())
            {
                logger.info("Player info is out of date.  Need to update!");
                // TODO: Notify or automatic update
            }
        }
    }

}

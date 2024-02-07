package com.sloppylinux.mchl.activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.ui.AppBarConfiguration;

import com.sloppylinux.mchl.domain.Player;
import com.sloppylinux.mchl.R;
import com.sloppylinux.mchl.util.Config;

import org.apache.log4j.Logger;

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

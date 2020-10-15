package com.sloppylinux.mchl.activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.ui.AppBarConfiguration;

import com.sloppylinux.mchl.domain.Game;
import com.sloppylinux.mchl.ui.R;
import com.sloppylinux.mchl.util.Config;

import org.apache.log4j.Logger;

public class ResultDisplay extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;

    private Config config;

    private Game game;

    private Logger logger = Logger.getLogger(ResultDisplay.class.getName());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        logger.debug("Displaying result");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup);
        Bundle bundle = getIntent().getExtras();
        game = (Game)bundle.get("game");

        config = new Config(this.getBaseContext());

        // TODO: Build the Result UI
    }

}

package com.sloppylinux.mchl.ui.settings;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.sloppylinux.mchl.domain.Player;
import com.sloppylinux.mchl.gui.R;
import com.sloppylinux.mchl.util.MCHLWebservice;

import java.util.List;

public class SettingsFragment extends Fragment {

    private SettingsViewModel settingsViewModel;

    private MCHLWebservice mchlWebservice;

    private Player player;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        String lookupName = "Kevin";

        settingsViewModel =
                ViewModelProviders.of(this).get(SettingsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_settings, container, false);
        final TextView textView = root.findViewById(R.id.text_settings);
        settingsViewModel.getText(lookupName).observe(this, new Observer<List<Player>>() {
            @Override
            public void onChanged(@Nullable List<Player> players) {
                StringBuilder sb = new StringBuilder();
                String separator = "";
                for (Player player : players)
                {
                    sb.append(separator);
                    sb.append(player.getShortInfo());
                    separator = "\n";
                }
                textView.setText(sb.toString());
            }
        });
        return root;
    }

}
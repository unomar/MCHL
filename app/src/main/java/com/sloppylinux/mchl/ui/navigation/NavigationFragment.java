package com.sloppylinux.mchl.ui.navigation;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.sloppylinux.mchl.domain.Player;
import com.sloppylinux.mchl.ui.R;
import com.sloppylinux.mchl.util.Config;

public class NavigationFragment extends Fragment {

    private NavigationViewModel navigationViewModel;

    private Config config;

    private Player player;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        config = new Config(this.getContext());

        if (config.isLoaded())
        {
            player = config.getPlayer();
        }

        navigationViewModel =
                new ViewModelProvider(this).get(NavigationViewModel.class);
        View root = inflater.inflate(R.layout.fragment_settings, container, false);
        final TextView playerNameView = root.findViewById(R.id.playerName);
        final TextView playerDivView = root.findViewById(R.id.playerDivision);
        playerNameView.setText(player.getName(" "));

        return root;
    }
}
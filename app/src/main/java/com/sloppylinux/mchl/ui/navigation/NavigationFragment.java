package com.sloppylinux.mchl.ui.navigation;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.sloppylinux.mchl.domain.Player;
import com.sloppylinux.mchl.util.Config;
import com.sloppylinux.mchl.databinding.NavHeaderNavigationBinding;


public class NavigationFragment extends Fragment {
    private NavHeaderNavigationBinding binding;
    private Config config;

    private Player player;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        config = new Config(this.getContext());
        binding = NavHeaderNavigationBinding.inflate(inflater, container, true);

        if (config.isLoaded())
        {
            player = config.getPlayer();
            binding.playerName.setText(player.getName(" "));
        }

        return binding.getRoot();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
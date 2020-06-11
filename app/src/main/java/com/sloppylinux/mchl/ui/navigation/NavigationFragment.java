package com.sloppylinux.mchl.ui.navigation;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.sloppylinux.mchl.domain.Player;
import com.sloppylinux.mchl.ui.R;
import com.sloppylinux.mchl.util.Config;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class NavigationFragment extends Fragment {

    private Config config;

    private Player player;
    private Unbinder unbinder;

    @BindView(R.id.playerName)
    TextView playerNameView;

    @BindView(R.id.playerDivision)
    TextView playerDivView;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        config = new Config(this.getContext());
        View root = inflater.inflate(R.layout.fragment_settings, container, false);
        unbinder = ButterKnife.bind(this, root);
        if (config.isLoaded())
        {
            player = config.getPlayer();
            playerNameView.setText(player.getName(" "));
        }

        return root;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }
}
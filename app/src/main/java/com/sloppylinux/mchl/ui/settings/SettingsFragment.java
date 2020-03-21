package com.sloppylinux.mchl.ui.settings;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.sloppylinux.mchl.activity.MchlNavigation;
import com.sloppylinux.mchl.domain.Player;
import com.sloppylinux.mchl.ui.R;
import com.sloppylinux.mchl.ui.common.adapters.PlayerListAdapter;
import com.sloppylinux.mchl.util.Config;

import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class SettingsFragment extends Fragment
{
    private SettingsViewModel settingsViewModel;
    private Config config;
    private Unbinder unbinder;

    @BindView(R.id.player_select_text)
    TextView playerSelectTextView;

    @BindView(R.id.playerList)
    ListView playerListView;

    @BindView((R.id.progressBar))
    ProgressBar spinner;

    @BindView(R.id.nameEntry)
    EditText editText;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState)
    {
        if (config == null)
        {
            config = new Config(this.getContext());
        }
        settingsViewModel =
                new ViewModelProvider(this).get(SettingsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_settings, container, false);
        unbinder = ButterKnife.bind(this, root);

        spinner.setVisibility(View.GONE);

        editText.setOnEditorActionListener(new TextView.OnEditorActionListener()
        {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event)
            {
                if (actionId == EditorInfo.IME_ACTION_SEARCH)
                {
                    playerSearch(v.getText().toString());
                    return true;
                }
                return false;
            }
        });

        return root;
    }

    public void playerSearch(String playerName)
    {
        spinner.setVisibility(View.VISIBLE);
        settingsViewModel.getText(playerName).observe(this, new Observer<List<Player>>()
        {
            @Override
            public void onChanged(@Nullable List<Player> players)
            {
                spinner.setVisibility(View.GONE);
                playerSelectTextView.setText(R.string.select_player_text);
                Collections.sort(players);
                PlayerListAdapter adapter = new PlayerListAdapter(players, getContext());
                playerListView.setAdapter(adapter);
                playerListView.setOnItemClickListener(new AdapterView.OnItemClickListener()
                {
                    @Override
                    public void onItemClick(AdapterView<?> adapter, View v, int pos, long position)
                    {
                        spinner.setVisibility(View.VISIBLE);
                        Player player = (Player) adapter.getItemAtPosition(pos);
                        settingsViewModel.getPlayerInfo(player).observe(getViewLifecycleOwner(), new Observer<String>()
                        {
                            @Override
                            public void onChanged(String s)
                            {
                                spinner.setVisibility(View.GONE);
                                Intent home = new Intent(getContext(), MchlNavigation.class);
                                startActivity(home);
                            }
                        });
                    }
                });
            }
        });
    }
}

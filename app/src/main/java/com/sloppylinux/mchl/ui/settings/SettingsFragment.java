package com.sloppylinux.mchl.ui.settings;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.sloppylinux.mchl.domain.Player;
import com.sloppylinux.mchl.gui.R;
import com.sloppylinux.mchl.util.Config;
import com.sloppylinux.mchl.util.MCHLWebservice;

import java.util.ArrayList;
import java.util.List;

public class SettingsFragment extends Fragment {

    private SettingsViewModel settingsViewModel;


    private ListView playerListView;

    private Config config;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        config = new Config(this.getContext());
        settingsViewModel =
                ViewModelProviders.of(this).get(SettingsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_settings, container, false);

        EditText editText = root.findViewById(R.id.nameEntry);
        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    playerSearch(v.getText().toString());
                    return true;
                }
                return false;
            }
        });

        playerListView = root.findViewById(R.id.playerList);

        return root;
    }

    public void playerSearch(String playerName)
    {

        settingsViewModel.getText(playerName).observe(this, new Observer<List<Player>>() {
            @Override
            public void onChanged(@Nullable List<Player> players) {
                List<String> playerNames = new ArrayList<>();
                for (Player player : players)
                {
                    playerNames.add(player.getShortInfo());
                }
//                textView.setText(sb.toString());
                ArrayAdapter<Player> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, players);
                playerListView.setAdapter(adapter);

                playerListView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
                    @Override
                    public void onItemClick(AdapterView<?>adapter,View v, int pos, long position){
                        Player player = (Player)adapter.getItemAtPosition(pos);
                        config.setPlayer(player);
                        config.storeValues();
                    }
                });
            }
        });
    }
}

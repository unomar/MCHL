package com.sloppylinux.mchl.ui.settings;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.snackbar.Snackbar;
import com.sloppylinux.mchl.activity.MchlNavigation;
import com.sloppylinux.mchl.domain.Player;
import com.sloppylinux.mchl.ui.R;
import com.sloppylinux.mchl.ui.common.adapters.PlayerListAdapter;
import com.sloppylinux.mchl.util.Config;
import com.sloppylinux.mchl.util.MCHLWebservice;
import com.sloppylinux.mchl.util.WebserviceException;

import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class SettingsFragment extends Fragment
{
    @BindView(R.id.player_select_text)
    TextView playerSelectTextView;
    @BindView(R.id.playerList)
    ListView playerListView;
    @BindView((R.id.progressBar))
    ProgressBar spinner;
    @BindView(R.id.nameEntry)
    EditText editText;
    private SettingsViewModel settingsViewModel;
    private Config config;
    private Unbinder unbinder;
    private Snackbar snackbar;

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
                    // Close the on-screen keyboard
                    editText.clearFocus();
                    InputMethodManager in = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    in.hideSoftInputFromWindow(editText.getWindowToken(), 0);

                    // Then issue the search
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
        snackbar = Snackbar.make(getView(), "Searching for " + playerName, Snackbar.LENGTH_INDEFINITE);
        snackbar.setAction("No action", null).show();
        spinner.setVisibility(View.VISIBLE);
        settingsViewModel.getText(playerName).observe(this, new Observer<List<Player>>()
        {
            @Override
            public void onChanged(@Nullable List<Player> players)
            {
                snackbar.dismiss();
                spinner.setVisibility(View.GONE);
                if (players != null && !players.isEmpty())
                {
                    playerSelectTextView.setText(R.string.select_player_text);
                    Collections.sort(players);
                    PlayerListAdapter adapter = new PlayerListAdapter(players, getContext());
                    playerListView.setAdapter(adapter);
                    playerListView.setOnItemClickListener(new AdapterView.OnItemClickListener()
                    {
                        @Override
                        public void onItemClick(AdapterView<?> adapter, View v, int pos, long position)
                        {
                            Player player = (Player) adapter.getItemAtPosition(pos);
                            snackbar = Snackbar.make(getView(), "Fetching schedule and stats for " + player.getName(), Snackbar.LENGTH_INDEFINITE);
                            snackbar.setAction("No action", null).show();
                            spinner.setVisibility(View.VISIBLE);

                            settingsViewModel.getPlayerInfo(player).observe(getViewLifecycleOwner(), new Observer<String>()
                            {
                                @Override
                                public void onChanged(String s)
                                {
                                    spinner.setVisibility(View.GONE);
                                    if (s.startsWith(WebserviceException.ERROR_PREFIX))
                                    {
                                        Toast toast = Toast.makeText(getContext(), s, Toast.LENGTH_LONG);
                                        toast.show();
                                    } else
                                    {
                                        Intent home = new Intent(getContext(), MchlNavigation.class);
                                        startActivity(home);
                                    }
                                }
                            });
                        }
                    });
                } else if (players != null) // Empty response means player query unsuccessful
                {
                    String message = "Unable to find any matching players";
                    playerSelectTextView.setText(message);
                } else // Null response means network error
                {
                    Toast toast = Toast.makeText(getContext(), MCHLWebservice.NETWORK_ERROR, Toast.LENGTH_LONG);
                    toast.show();
                }
            }
        });
    }
}

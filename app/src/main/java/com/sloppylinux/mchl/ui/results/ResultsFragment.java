package com.sloppylinux.mchl.ui.results;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.sloppylinux.mchl.domain.Game;
import com.sloppylinux.mchl.ui.R;
import com.sloppylinux.mchl.ui.schedule.GameListAdapter;
import com.sloppylinux.mchl.util.Config;

import java.util.List;

public class ResultsFragment extends Fragment {

    private ListView resultListView;

    private Config config;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        if (config == null) {
            config = new Config(this.getContext());
        }

        View root = inflater.inflate(R.layout.fragment_results, container, false);

        resultListView = root.findViewById(R.id.resultList);

        if (config.getPlayer() != null)
        {
            GameListAdapter adapter = new GameListAdapter(config.getPlayer().getPlayerResultList(), getContext());
            resultListView.setAdapter(adapter);
        }

        else {

        }

        return root;
    }
}
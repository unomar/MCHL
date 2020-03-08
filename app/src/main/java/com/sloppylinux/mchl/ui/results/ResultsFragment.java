package com.sloppylinux.mchl.ui.results;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.sloppylinux.mchl.ui.R;
import com.sloppylinux.mchl.ui.adapters.GameListAdapter;
import com.sloppylinux.mchl.util.Config;

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
package com.sloppylinux.mchl.ui.results;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.snackbar.Snackbar;
import com.sloppylinux.mchl.activity.ResultDisplay;
import com.sloppylinux.mchl.domain.Game;
import com.sloppylinux.mchl.ui.R;
import com.sloppylinux.mchl.ui.common.adapters.GameListAdapter;
import com.sloppylinux.mchl.ui.common.fragments.RefreshFragment;
import com.sloppylinux.mchl.util.Config;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ResultsFragment extends RefreshFragment
{
    private GameListAdapter adapter;

    @BindView(R.id.resultList)
    ListView resultListView;

    @BindView(R.id.resultRefreshView)
    SwipeRefreshLayout refreshLayout;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState)
    {

        if (config == null)
        {
            config = new Config(this.getContext());
        }

        View root = inflater.inflate(R.layout.fragment_results, container, false);
        unbinder = ButterKnife.bind(this, root);
        super.setup(refreshLayout);

        if (config.getPlayer() != null)
        {
            adapter = new GameListAdapter(config.getPlayer().getPlayerResultList(), getContext());
            resultListView.setAdapter(adapter);
            resultListView.setOnItemClickListener(new AdapterView.OnItemClickListener()
            {

                @Override
                public void onItemClick(AdapterView<?> adapterView, View v, int index,
                                        long arg3)
                {
                    adapterView.callOnClick();
                    Game game = (Game) adapterView.getItemAtPosition(index);
                    Snackbar.make(v, "Loading Match Info for " + game.getDateString(), Snackbar.LENGTH_LONG)
                            .setAction("No action", null).show();

                    Intent intent = new Intent(getContext(), ResultDisplay.class);
                    Bundle b = new Bundle();
                    b.putSerializable("game", game);
                    intent.putExtras(b);
                    getContext().startActivity(intent);
                }
            });
        }

        return root;
    }

    @Override
    public void refreshView()
    {
        adapter.clear();
        adapter.addAll(config.getPlayer().getPlayerResultList());
        adapter.notifyDataSetChanged();
    }
}
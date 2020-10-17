package com.sloppylinux.mchl.ui.common.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.sloppylinux.mchl.domain.Team;
import com.sloppylinux.mchl.ui.R;
import com.sloppylinux.mchl.ui.common.adapters.TeamPlayersListAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class TeamFragment extends Fragment
{
    public static final String TEAM_KEY = "team";
    private Unbinder unbinder;

    @BindView(R.id.teamPlayerList)
    ListView teamPlayerListView;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState)
    {
        View root = inflater.inflate(R.layout.fragment_team, container, false);
        unbinder = ButterKnife.bind(this, root);

        return root;
    }

    public void onViewCreated(View view, Bundle bundle)
    {
        super.onViewCreated(view, bundle);
        Team team = (Team) getArguments().get(TEAM_KEY);

        TeamPlayersListAdapter gamePagerAdapter = new TeamPlayersListAdapter(team, getContext());
        teamPlayerListView.setAdapter(gamePagerAdapter);
    }
}

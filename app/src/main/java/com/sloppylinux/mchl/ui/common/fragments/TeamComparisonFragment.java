package com.sloppylinux.mchl.ui.common.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.sloppylinux.mchl.ui.R;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public class TeamComparisonFragment extends Fragment
{
    public static final String GAME_KEY = "game";
    private Unbinder unbinder;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState)
    {
        View root = inflater.inflate(R.layout.fragment_team_comparison, container, false);
        unbinder = ButterKnife.bind(this, root);

        return root;
    }

    public void onViewCreated(View view, Bundle bundle)
    {
        // TODO: Populate view
    }
}

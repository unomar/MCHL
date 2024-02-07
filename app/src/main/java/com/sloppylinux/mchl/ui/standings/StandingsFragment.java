package com.sloppylinux.mchl.ui.standings;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.sloppylinux.mchl.util.Config;
import com.sloppylinux.mchl.databinding.FragmentStandingsBinding;


public class StandingsFragment extends Fragment
{
    private FragmentStandingsBinding binding;

    private StandingsPagerAdapter standingsPagerAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        binding = FragmentStandingsBinding.inflate(inflater, container, true);
//        View root = inflater.inflate(R.layout.fragment_standings, container, false);
//        unbinder = ButterKnife.bind(this, root);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        Config config = new Config(getContext());
        if (config.getLeagueTables() != null)
        {
            standingsPagerAdapter = new StandingsPagerAdapter(config.getLeagueTables(), getChildFragmentManager());
            binding.standingsPager.setAdapter(standingsPagerAdapter);

            binding.tabLayout.setupWithViewPager(binding.standingsPager);
        }
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
    }
}



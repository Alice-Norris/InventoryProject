package com.example.myapplication;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class CollectionWelcomeFragment extends Fragment{
    WelcomeFragmentStateAdapter welcomeFragmentStateAdapter;
    ViewPager2 welcomeViewPager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                                @Nullable Bundle savedInstanceBundle){
        return inflater.inflate(R.layout.welcome, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState){
        welcomeFragmentStateAdapter = new WelcomeFragmentStateAdapter(this);
        welcomeViewPager = view.findViewById(R.id.welcomeViewPager);
        welcomeViewPager.setAdapter(welcomeFragmentStateAdapter);

        TabLayout welcomeTabLayout = view.findViewById(R.id.tabs);
        String[] tabNames = {"Gruxafixuie", "Makorkha"};
        new TabLayoutMediator(welcomeTabLayout, welcomeViewPager,
                (tab, position)->tab.setText(tabNames[position])
                ).attach();
    }
}

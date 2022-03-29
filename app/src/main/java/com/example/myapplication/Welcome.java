package com.example.myapplication;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

public class Welcome extends FragmentActivity {
    private static final int NUM_PAGES = 2;

    private ViewPager2 welcomePager;

    private FragmentStateAdapter welcomePagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome);

        welcomePager = findViewById(R.id.tabs);
        welcomePagerAdapter = new WelcomePagerAdapter(this);
        welcomePager.setAdapter(welcomePagerAdapter);
    }

    private class WelcomePagerAdapter extends FragmentStateAdapter {
        public WelcomePagerAdapter(FragmentActivity fa){
            super(fa);
        }

        @Override
        public Fragment createFragment(int tabPosition){
            return new WelcomeScreenFragment();
        }

        @Override
        public int getItemCount(){
            return NUM_PAGES;
        }
    }
}

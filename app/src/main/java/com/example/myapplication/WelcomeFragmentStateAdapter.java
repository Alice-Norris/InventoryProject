package com.example.myapplication;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class WelcomeFragmentStateAdapter extends FragmentStateAdapter {
    public WelcomeFragmentStateAdapter(Fragment fragment) {
        super(fragment);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position){
        Fragment fragment = new WelcomeScreenFragment();
        Bundle args = new Bundle();
        args.putInt(WelcomeScreenFragment.ARG_OBJECT, position + 1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getItemCount(){
        return 2;
    }
}

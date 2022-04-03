package com.example.ProjectOneApplication;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class WelcomeFragmentStateAdapter extends FragmentStateAdapter {
    public WelcomeFragmentStateAdapter(FragmentManager fragmentManager, Lifecycle lifecycle){
        super(fragmentManager, lifecycle);
    }

    @Override
    @NonNull
    public Fragment createFragment(int position){
        if (position == 0) {
            return new LoginFragment();
        } else {
            return new RegisterFragment();
        }
    }

    @Override
    public int getItemCount(){
        return 2;
    }
}

package com.alice_norris.inventoryproject.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.alice_norris.inventoryproject.fragments.LoginFragment;
import com.alice_norris.inventoryproject.fragments.RegisterFragment;

//creating subclass of FragmentStateAdapter, works with the ViewPager2 on the login activity.
public class WelcomeFragmentStateAdapter extends FragmentStateAdapter {
    public WelcomeFragmentStateAdapter(FragmentManager fragmentManager, Lifecycle lifecycle){
        super(fragmentManager, lifecycle);
    }

    //returns proper fragment as needed
    @Override
    @NonNull
    public Fragment createFragment(int position){
        if (position == 0) {
            return new LoginFragment();
        } else {
            return new RegisterFragment();
        }
    }

    //limited to two items
    @Override
    public int getItemCount(){
        return 2;
    }
}

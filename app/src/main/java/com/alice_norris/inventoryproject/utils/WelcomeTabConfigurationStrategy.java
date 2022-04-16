package com.alice_norris.inventoryproject.utils;

import androidx.annotation.NonNull;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

//implementation of TabConfigurationStrategy, to be used with WelcomeFragmentStateAdapter
public class WelcomeTabConfigurationStrategy implements TabLayoutMediator.TabConfigurationStrategy{
    public void onConfigureTab(TabLayout.Tab tab, int position){
        if (position == 0){
            tab.setText("Login");
        } else {
            tab.setText("Register");
        }
    }
}

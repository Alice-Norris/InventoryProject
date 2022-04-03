package com.example.ProjectOneApplication;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class WelcomeTabConfigurationStrategy implements TabLayoutMediator.TabConfigurationStrategy{
    public void onConfigureTab(TabLayout.Tab tab, int position){
        if (position == 0){
            tab.setText("Login");
        } else {
            tab.setText("Register");
        }
    }
}

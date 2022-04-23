package com.alice_norris.inventoryproject.activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import com.alice_norris.inventoryproject.adapters.WelcomeFragmentStateAdapter;
import com.alice_norris.inventoryproject.datamodels.LoginViewModel;
import com.alice_norris.inventoryproject.R;
import com.alice_norris.inventoryproject.utils.WelcomeTabConfigurationStrategy;

public class LoginActivity extends AppCompatActivity {

    private LoginViewModel loginViewModel;

    @Override
    public void onCreate(Bundle login) {
        super.onCreate(login);
        setContentView(R.layout.login);

        //getting toolbar and setting it as the activity's action bar
        MaterialToolbar welcomeToolbar = findViewById(R.id.welcomeToolbar);
        setSupportActionBar(welcomeToolbar);

        //create fragment adapter to handle fragments for the pager
        WelcomeFragmentStateAdapter welcomeAdapter = new WelcomeFragmentStateAdapter(
                getSupportFragmentManager(), getLifecycle());

        //getting viewPager and setting adapter
        ViewPager2 welcomePager = findViewById(R.id.welcomePager);
        welcomePager.setAdapter(welcomeAdapter);

        //creating and attaching tab layout mediator to link tabs to viewpager
        TabLayout welcomeTabs = findViewById(R.id.welcomeTabs);
        WelcomeTabConfigurationStrategy welcomeTabConfigure = new WelcomeTabConfigurationStrategy();
        new TabLayoutMediator(welcomeTabs, welcomePager, welcomeTabConfigure).attach();

        LoginViewModel loginViewModel = new ViewModelProvider(this).get(LoginViewModel.class);

    }
}



package com.alice_norris.inventoryproject.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alice_norris.inventoryproject.datamodels.LoginViewModel;
import com.alice_norris.inventoryproject.datamodels.User;
import com.alice_norris.inventoryproject.datamodels.UserRepository;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.widget.ViewPager2;

import com.alice_norris.inventoryproject.R;
import com.alice_norris.inventoryproject.adapters.WelcomeFragmentStateAdapter;
import com.alice_norris.inventoryproject.utils.WelcomeTabConfigurationStrategy;
import com.google.android.material.textfield.TextInputEditText;


public class LoginActivity extends AppCompatActivity {

    private LoginViewModel loginViewModel;


    protected void onCreate(Bundle login) {
        super.onCreate(login);
        setContentView(R.layout.login);

        loginViewModel = new ViewModelProvider(this).get(LoginViewModel.class);
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
    }
}



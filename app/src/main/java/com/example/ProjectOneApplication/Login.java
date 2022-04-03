package com.example.ProjectOneApplication;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.Button;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout.LayoutParams;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class Login extends AppCompatActivity {

    WelcomeFragmentStateAdapter welcomeAdapter;

    protected void onCreate(Bundle login){
        super.onCreate(login);
        setContentView(R.layout.login);

        //create fragment adapter to handle fragments for the pager
        welcomeAdapter = new WelcomeFragmentStateAdapter(getSupportFragmentManager(), getLifecycle());

        //getting viewPager and setting adapter
        ViewPager2 welcomePager = (ViewPager2) findViewById(R.id.welcomePager);
        welcomePager.setAdapter(welcomeAdapter);

        //creating and attaching tab layout mediator to link tabs to viewpager
        TabLayout welcomeTabs = findViewById(R.id.welcomeTabs);
        WelcomeTabConfigurationStrategy welcomeTabConfigure = new WelcomeTabConfigurationStrategy();
        new TabLayoutMediator(welcomeTabs, welcomePager, welcomeTabConfigure).attach();


    }
}

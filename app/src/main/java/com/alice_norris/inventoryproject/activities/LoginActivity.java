package com.alice_norris.inventoryproject.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.alice_norris.inventoryproject.datamodels.LoginViewModel;
import com.alice_norris.inventoryproject.datamodels.User;
import com.alice_norris.inventoryproject.datamodels.UserRepository;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentResultListener;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.widget.ViewPager2;

import com.alice_norris.inventoryproject.R;
import com.alice_norris.inventoryproject.adapters.WelcomeFragmentStateAdapter;
import com.alice_norris.inventoryproject.utils.WelcomeTabConfigurationStrategy;


public class LoginActivity extends AppCompatActivity {

    private LoginViewModel loginViewModel;
    private User currentUser;
    protected void onCreate(Bundle login){
        super.onCreate(login);
        setContentView(R.layout.login);

        loginViewModel = new ViewModelProvider(this).get(LoginViewModel.class);
        loginViewModel.getUser().observe(this, new Observer<User>() {
            @Override
            public void onChanged(@Nullable User userData){
                currentUser = userData;
            }
        });
        //getting toolbar and setting it as the activity's action bar
        MaterialToolbar welcomeToolbar = findViewById(R.id.welcomeToolbar);
        setSupportActionBar(welcomeToolbar);

        //setting up login/register fragment listeners
        FragmentResultListener loginFragResultListener =
                new LoginFragmentResultListener();
        getSupportFragmentManager()
                .setFragmentResultListener("loginRequest", this,
                                            loginFragResultListener);
        getSupportFragmentManager()
                .setFragmentResultListener("registerRequest", this,
                        loginFragResultListener);

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

    private class LoginFragmentResultListener implements FragmentResultListener {
        String username;
        String password;
        String firstName;

        @Override
        public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle bundle) {


            if (requestKey.equals("loginRequest")) {
                username = bundle.getString("username");
                password = bundle.getString("password");
            } else if (requestKey.equals("registerRequest")) {
                firstName = bundle.getString("firstName");
                String lastName = bundle.getString("lastName");
                username = bundle.getString("username");
                password = bundle.getString("password");
                User newUser = new User(firstName, lastName, username, password);
                loginViewModel.register(newUser);
            }
            loginViewModel.login(username, password);
            Intent returnIntent = new Intent(getApplicationContext(), MainActivity.class);
            setResult(Activity.RESULT_OK, returnIntent);
        }
    }
}

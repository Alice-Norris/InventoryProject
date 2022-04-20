package com.alice_norris.inventoryproject.fragments;

import static android.view.View.VISIBLE;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.alice_norris.inventoryproject.R;
import com.alice_norris.inventoryproject.datamodels.LoginViewModel;
import com.alice_norris.inventoryproject.datamodels.User;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.progressindicator.CircularProgressIndicator;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.Iterator;

//implementation of fragment to be used with the login activity
public class RegisterFragment extends Fragment{
    private LoginViewModel registerViewModel;
    private TextInputEditText firstNameInput;
    private TextInputEditText lastNameInput;
    private TextInputEditText usernameInput;
    private TextInputEditText passwdInput;
    private String firstName;
    private String lastName;
    private String username;
    private String password;
    private View registerView;
    private MaterialButton registerBtn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        registerViewModel = new ViewModelProvider(getActivity()).get(LoginViewModel.class);
        registerView = inflater.inflate(R.layout.register_fragment, container, false);
        return registerView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle SavedInstanceState) {
        firstNameInput = getView().findViewById(R.id.firstNameInput);
        lastNameInput = getView().findViewById(R.id.lastNameInput);
        usernameInput = getView().findViewById(R.id.registerUsernameInput);
        passwdInput = getView().findViewById(R.id.registerPasswdInput);
        registerBtn = getView().findViewById(R.id.registerButton);
        registerBtn.setOnClickListener(button -> {
            firstName = firstNameInput.getText().toString();
            lastName = lastNameInput.getText().toString();
            username = usernameInput.getText().toString();
            password = passwdInput.getText().toString();
            User newUser = new User(firstName, lastName, username, password);
            registerViewModel.register(newUser);
            registerViewModel.login(username, password).observe(getViewLifecycleOwner(),
                user -> {
                    Intent returnIntent = new Intent();
                    returnIntent.putExtra("userFirstName", user.userFirstName);
                    getActivity().setResult(Activity.RESULT_OK, returnIntent);
                    getActivity().finish();
                });
        });
    }
}


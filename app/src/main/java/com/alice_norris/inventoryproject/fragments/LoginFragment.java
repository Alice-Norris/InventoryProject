package com.alice_norris.inventoryproject.fragments;

import static android.app.Activity.RESULT_OK;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.alice_norris.inventoryproject.R;
import com.alice_norris.inventoryproject.datamodels.LoginViewModel;
import com.alice_norris.inventoryproject.datamodels.User;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;


public class LoginFragment extends Fragment {
    TextInputEditText usernameInput;
    TextInputEditText passwordInput;
    View loginView;
    String username;
    String password;
    private MaterialButton loginButton;
    private LoginViewModel loginFragmentViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedStateInstance) {
        loginView = inflater.inflate(R.layout.login_fragment, container, false);
        return loginView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle SavedInstanceState) {
        loginButton = view.findViewById(R.id.loginButton);
        loginButton.setOnClickListener(button ->{
            loginFragmentViewModel = new ViewModelProvider(getActivity()).get(LoginViewModel.class);
            usernameInput = getView().findViewById(R.id.usernameInput);
            passwordInput = getView().findViewById(R.id.passwdInput);
            username = usernameInput.getText().toString();
            password = passwordInput.getText().toString();
            loginFragmentViewModel.login(username, password).observe(getViewLifecycleOwner(),
                    user ->{
                        if (user != null){
                            Intent returnIntent = new Intent();
                            returnIntent.putExtra("userFirstName", user.userFirstName);
                            getActivity().setResult(RESULT_OK, returnIntent);
                            getActivity().finish();
                        }
                    });
        });
    }
}
package com.alice_norris.inventoryproject.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.alice_norris.inventoryproject.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;


public class LoginFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                            Bundle savedStateInstance){
        return (ViewGroup) inflater.inflate(R.layout.login_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle SavedInstanceState){
        MaterialButton loginBtn = (MaterialButton) getView().findViewById(R.id.loginButton);
        loginBtn.setOnClickListener(new LoginClickHandler());
    }

    private class LoginClickHandler implements View.OnClickListener{
        TextInputEditText usernameInput;
        TextInputEditText passwordInput;

        public void onClick(View button){
            usernameInput = (TextInputEditText) getView().findViewById(R.id.usernameInput);
            passwordInput = (TextInputEditText) getView().findViewById(R.id.passwdInput);

            Bundle loginBundle = new Bundle();
            String username= usernameInput.getText().toString();
            String password = passwordInput.getText().toString();
            loginBundle.putString("username", username);
            loginBundle.putString("password", password);
            getParentFragmentManager().setFragmentResult("loginRequest", loginBundle);
        }
    }


}

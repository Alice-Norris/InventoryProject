package com.alice_norris.inventoryproject.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.alice_norris.inventoryproject.R;
import com.alice_norris.inventoryproject.datamodels.User;
import com.alice_norris.inventoryproject.utils.PasswordHasher;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

//implementation of fragment to be used with the login activity
public class RegisterFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        return (ViewGroup) inflater.inflate(R.layout.register_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle SavedInstanceState){
        MaterialButton registerBtn = (MaterialButton) getView().findViewById(R.id.registerButton);
        registerBtn.setOnClickListener(new RegisterClickHandler());
    }

    private class RegisterClickHandler implements View.OnClickListener{
        TextInputEditText firstNameInput;
        TextInputEditText lastNameInput;
        TextInputEditText usernameInput;
        TextInputEditText passwdInput;
        boolean loggedIn;

        public RegisterClickHandler(){
            firstNameInput = (TextInputEditText) getView().findViewById(R.id.firstNameInput);
            lastNameInput = (TextInputEditText) getView().findViewById(R.id.lastNameInput);
            usernameInput = (TextInputEditText) getView().findViewById(R.id.registerUsernameInput);
            passwdInput = (TextInputEditText) getView().findViewById(R.id.registerPasswdInput);
        }

        public void onClick(View button){

            Bundle registerData = new Bundle();
            registerData.putString("firstName", firstNameInput.getText().toString());
            registerData.putString("lastName", lastNameInput.getText().toString());
            registerData.putString("username", usernameInput.getText().toString());
            registerData.putString("password", passwdInput.getText().toString());
            getParentFragmentManager().setFragmentResult("registerRequest", registerData);
        }
    }
}

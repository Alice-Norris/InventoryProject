package com.alice_norris.inventoryproject.fragments;

import android.app.Activity;
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
import com.alice_norris.inventoryproject.utils.PasswordHasher;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

//implementation of fragment to be used with the login activity
public class RegisterFragment extends Fragment {
    private LoginViewModel registerViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        return (ViewGroup) inflater.inflate(R.layout.register_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle SavedInstanceState){
        MaterialButton registerBtn = (MaterialButton) getView().findViewById(R.id.registerButton);
        registerBtn.setOnClickListener(new RegisterClickHandler());
        registerViewModel = new ViewModelProvider(requireActivity()).get(LoginViewModel.class);
        registerViewModel.getUser().observe(getActivity(), new Observer<User>() {
            @Override
            public void onChanged(User user) {
                if (!user.userFirstName.equals("null")) {
                    getActivity().setResult(Activity.RESULT_OK);
                    getActivity().finish();
                }
            }
        });
    }

    private class RegisterClickHandler implements View.OnClickListener{
        TextInputEditText firstNameInput;
        TextInputEditText lastNameInput;
        TextInputEditText usernameInput;
        TextInputEditText passwdInput;
        boolean loggedIn;

        public RegisterClickHandler(){
            firstNameInput = getView().findViewById(R.id.firstNameInput);
            lastNameInput = getView().findViewById(R.id.lastNameInput);
            usernameInput = getView().findViewById(R.id.registerUsernameInput);
            passwdInput = getView().findViewById(R.id.registerPasswdInput);
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

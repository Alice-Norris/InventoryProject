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
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;


public class LoginFragment extends Fragment {
    private LoginViewModel loginFragmentViewModel;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                            Bundle savedStateInstance){
        return (ViewGroup) inflater.inflate(R.layout.login_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle SavedInstanceState){
        loginFragmentViewModel = new ViewModelProvider(requireActivity()).get(LoginViewModel.class);
        loginFragmentViewModel.getUser().observe(getActivity(), new Observer<User>() {
            @Override
            public void onChanged(User user) {
                if(!user.userFirstName.equals("null")){
                    getActivity().setResult(Activity.RESULT_OK);
                    getActivity().finish();
                }
            }
        });
        MaterialButton loginBtn = (MaterialButton) getView().findViewById(R.id.loginButton);
        loginBtn.setOnClickListener(new LoginClickHandler());

    }

    private class LoginClickHandler implements View.OnClickListener{
        TextInputEditText usernameInput;
        TextInputEditText passwordInput;
        public void onClick(View button){
            usernameInput = (TextInputEditText) getView().findViewById(R.id.usernameInput);
            passwordInput = (TextInputEditText) getView().findViewById(R.id.passwdInput);
            String username= usernameInput.getText().toString();
            String password = passwordInput.getText().toString();
            loginFragmentViewModel.login(username, password);
        }
    }
}

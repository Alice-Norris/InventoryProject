package com.example.ProjectOneApplication;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.fragment.app.Fragment;

import com.example.ProjectOneApplication.R;

public class LoginFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                            Bundle savedStateInstance){
        Button loginButton = (Button) getView().findViewById(R.id.loginButton);

        return (ViewGroup) inflater.inflate(R.layout.login_fragment, container, false);


    }
    private class LoginClickHandler implements View.OnClickListener{
        public void onClick(View button){
            EditText userIdInput = (EditText) getView().findViewById(R.id.usernameInput);
            EditText passwdInput = (EditText) getView().findViewById(R.id.passwdInput);
            String userId = userIdInput.getText().toString();
            String passwd = passwdInput.getText().toString();


        }
    }
}

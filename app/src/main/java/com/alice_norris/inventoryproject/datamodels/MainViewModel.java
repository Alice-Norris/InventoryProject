package com.alice_norris.inventoryproject.datamodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MainViewModel extends ViewModel {
    private final MutableLiveData<Boolean> loggedIn;

    public MainViewModel(){
        this.loggedIn = new MutableLiveData<Boolean>(false);
    }

    public void loginStatusChange(){
        if(loggedIn.getValue() == false){
            loggedIn.setValue(true);
        } else {
            loggedIn.setValue(false);
        }
    }
    public LiveData<Boolean> getLoginStatus(){
        return this.loggedIn;
    }
}

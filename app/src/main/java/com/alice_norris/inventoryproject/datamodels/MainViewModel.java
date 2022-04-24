package com.alice_norris.inventoryproject.datamodels;

import android.Manifest;

import androidx.core.content.ContextCompat;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MainViewModel extends ViewModel {
    private final MutableLiveData<Boolean> loggedIn;
    private String userFirstName;
    private boolean notifyDeniedPreviously = false;
    private boolean notifyPermissionAllowed;
    public MainViewModel(){
        this.loggedIn = new MutableLiveData<>(false);
    }

    public void loginStatusChange(){
        if(!loggedIn.getValue()){
            loggedIn.setValue(true);
        } else {
            loggedIn.setValue(false);
        }
    }

    public LiveData<Boolean> getLoginStatus(){
        return this.loggedIn;
    }

    public void setUserFirstName(String userFirstName) { this.userFirstName = userFirstName; }

    public String getUserFirstName() {return this.userFirstName; }

    public void logout(){
        loggedIn.setValue(false);
    }

    public void setNotificationPermission(Boolean permission){
        this.notifyPermissionAllowed = permission;
    }

    public Boolean getNotificationPermission(){
        return notifyPermissionAllowed;
    }

    public void setNotifyDeniedPreviously(Boolean denied){
        this.notifyDeniedPreviously = denied;
    }

    public boolean isNotifyDeniedPreviously() {
        return notifyDeniedPreviously;
    }


}

package com.example.dagger2_java.ui.main.profile;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.dagger2_java.SessionManager;
import com.example.dagger2_java.model.User;
import com.example.dagger2_java.ui.auth.AuthResource;

import javax.inject.Inject;


public class ProfileViewModel extends ViewModel {

    private static final String TAG = "ProfileViewModel";
    private final SessionManager sessionManager;

    @Inject
    public ProfileViewModel(SessionManager sessionManager){
        this.sessionManager = sessionManager;
        Log.e(TAG,"ProfileViewModel:" + "is working!");
    }

    public LiveData<AuthResource<User>> getAuthenticatedUser(){
        return sessionManager.getAuthUser();
    }

}

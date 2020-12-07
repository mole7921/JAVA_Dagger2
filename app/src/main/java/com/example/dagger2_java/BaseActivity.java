package com.example.dagger2_java;



import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;


import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;

import com.example.dagger2_java.model.User;
import com.example.dagger2_java.ui.auth.AuthActivity;
import com.example.dagger2_java.ui.auth.AuthResource;

import javax.inject.Inject;

import dagger.android.support.DaggerAppCompatActivity;

public class BaseActivity extends DaggerAppCompatActivity {

    private static final String TAG = "BaseActivity";
    @Inject
    public SessionManager sessionManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        subscribeObservers();
    }

    private void subscribeObservers(){
        sessionManager.getAuthUser().observe(this, new Observer<AuthResource<User>>() {
            @Override
            public void onChanged(AuthResource<User> userAuthResource) {
                if(userAuthResource != null){
                    switch (userAuthResource.status){
                        case LOADING:{
                            break;
                        }

                        case AUTHENTICATED:{
                            Log.e(TAG,"LoginSuccess:"+userAuthResource.data.getEmail());

                            break;
                        }

                        case ERROR:{
                            Log.e(TAG,"LoginError:"+userAuthResource.message);

                            break;
                        }

                        case NOT_AUTHENTICATED:{
                            navLoginScreen();
                            break;
                        }
                    }
                }
            }
        });
    }


    private void navLoginScreen(){
        Intent intent = new Intent(this,AuthActivity.class);
        startActivity(intent);
        finish();
    }

}

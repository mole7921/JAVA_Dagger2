package com.example.dagger2_java.ui.auth;



import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.lifecycle.ViewModelStore;

import com.bumptech.glide.RequestManager;
import com.example.dagger2_java.R;
import com.example.dagger2_java.model.User;
import com.example.dagger2_java.ui.main.MainActivity;
import com.example.dagger2_java.viewmodels.ViewModelProviderFactory;

import org.w3c.dom.Text;

import javax.inject.Inject;
import javax.inject.Named;

import dagger.android.support.DaggerAppCompatActivity;

public class AuthActivity extends DaggerAppCompatActivity implements View.OnClickListener
{

    private static final String TAG = "AuthActivity";
    private AuthViewModel viewModel;
    private ProgressBar progressBar;
    private EditText userId;
    private Button btn;


    @Inject
    ViewModelProviderFactory providerFactory;

    @Inject
    Drawable drawable;

    @Inject
    RequestManager requestManager;


    //比較scope作用域不同
    @Inject
    @Named("AuthScopeUser")
    User user1;

    @Inject
    @Named("SingletonUser")
    User user2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);
        userId = findViewById(R.id.user_id_input);
        btn = findViewById(R.id.login_button);
        btn.setOnClickListener(this);
        progressBar = findViewById(R.id.progress_bar);

        viewModel = new ViewModelProvider(this,providerFactory).get(AuthViewModel.class);

        setImage();

        subscribeObservers();

        //比較記憶體位置後可知Singleton復用,scope則重新產生
        Log.e(TAG,"onCreate:" + user1);
        Log.e(TAG,"onCreate:" + user2);
    }

    private void subscribeObservers(){
        viewModel.observeAuthState().observe(this, new Observer<AuthResource<User>>() {
            @Override
            public void onChanged(AuthResource<User> userAuthResource) {
                if(userAuthResource != null){
                    switch (userAuthResource.status){
                        case LOADING:{
                            showProgressBar(true);
                            break;
                        }

                        case AUTHENTICATED:{
                            showProgressBar(false);
                            Log.e(TAG,"LoginSuccess:"+userAuthResource.data.getEmail());
                            onLoginSuccess();
                            break;
                        }

                        case ERROR:{
                            showProgressBar(false);
                            Toast.makeText(AuthActivity.this,userAuthResource.message +
                                    "\nDid you enter a number between 1 to 10 ?",Toast.LENGTH_SHORT).show();
                            break;
                        }

                        case NOT_AUTHENTICATED:{
                            showProgressBar(false);
                            break;
                        }
                    }
                }
            }
        });
    }

    private void onLoginSuccess(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    private void showProgressBar(boolean isVisible){
        if(isVisible){
            progressBar.setVisibility(View.VISIBLE);
        }else{
            progressBar.setVisibility(View.GONE);
        }

    }

    private void setImage() {
        requestManager
                .load(drawable)
                .into((ImageView)findViewById(R.id.login_logo));
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.login_button: {
                attemptLogin();
                break;
            }

        }
    }

    private void attemptLogin() {
        if(TextUtils.isEmpty(userId.getText().toString())){
            return;
        }
        viewModel.fetchById(Integer.parseInt(userId.getText().toString()));
    }
}
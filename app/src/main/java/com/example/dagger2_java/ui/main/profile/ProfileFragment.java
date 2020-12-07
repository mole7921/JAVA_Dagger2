package com.example.dagger2_java.ui.main.profile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.dagger2_java.R;
import com.example.dagger2_java.model.User;
import com.example.dagger2_java.ui.auth.AuthResource;
import com.example.dagger2_java.viewmodels.ViewModelProviderFactory;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;

public class ProfileFragment extends DaggerFragment {

    private static final String TAG = "ProfileFragment";
    private ProfileViewModel viewModel;
    private TextView email,username,website;

    @Inject
    ViewModelProviderFactory providerFactory;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel =new ViewModelProvider(this,providerFactory).get(ProfileViewModel.class);

        email = view.findViewById(R.id.email);
        username = view.findViewById(R.id.username);
        website = view.findViewById(R.id.website);

        subscribeObserver();

    }


    private void subscribeObserver(){
        viewModel.getAuthenticatedUser().removeObservers(getViewLifecycleOwner());
        viewModel.getAuthenticatedUser().observe(getViewLifecycleOwner(), new Observer<AuthResource<User>>() {
            @Override
            public void onChanged(AuthResource<User> userAuthResource) {
               if(userAuthResource != null){
                   switch (userAuthResource.status){
                       case AUTHENTICATED:{
                            setUserDetail(userAuthResource.data);
                            break;
                       }

                       case ERROR:{
                            setErrorDetail(userAuthResource.message);
                            break;
                       }
                   }
               }
            }
        });
    }

    private void setErrorDetail(String message) {
        email.setText(message);
        username.setText("error");
        website.setText("error");
    }

    private void setUserDetail(User data) {
        email.setText(data.getEmail());
        username.setText(data.getUsername());
        website.setText(data.getWebsite());
    }
}

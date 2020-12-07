package com.example.dagger2_java.di.auth;

import com.example.dagger2_java.model.User;
import com.example.dagger2_java.network.auth.AuthApi;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

@Module
public class AuthModule {

    @AuthScope
    @Provides
    static AuthApi provideAuthApi(Retrofit retrofit){
        return retrofit.create(AuthApi.class);
    }

    @AuthScope
    @Provides
    @Named("AuthScopeUser")
    static User someUser(){
        return new User();
    }
}

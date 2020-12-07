package com.example.dagger2_java.di;

import com.example.dagger2_java.di.auth.AuthModule;
import com.example.dagger2_java.di.auth.AuthViewModelsModule;
import com.example.dagger2_java.di.main.MainFragmentBuildersModule;
import com.example.dagger2_java.di.main.MainModule;
import com.example.dagger2_java.di.main.MainViewModelsModule;
import com.example.dagger2_java.ui.auth.AuthActivity;
import com.example.dagger2_java.ui.main.MainActivity;


import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityBuildersModule {

    @ContributesAndroidInjector(
            modules = {
                    AuthViewModelsModule.class,
                    AuthModule.class,
            })
    abstract AuthActivity contributeAuthActivity();

    @ContributesAndroidInjector(
            modules = {
                    MainFragmentBuildersModule.class,
                    MainViewModelsModule.class,
                    MainModule.class,
            }
    )
    abstract MainActivity contributeMainActivity();

}

/*
 same like subComponents
 */
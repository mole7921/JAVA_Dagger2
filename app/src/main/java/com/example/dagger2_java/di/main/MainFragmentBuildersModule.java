package com.example.dagger2_java.di.main;



import com.example.dagger2_java.ui.main.posts.PostsFragment;
import com.example.dagger2_java.ui.main.profile.ProfileFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class MainFragmentBuildersModule {


    @ContributesAndroidInjector
    abstract ProfileFragment contributeProfileFragment();

    @ContributesAndroidInjector
    abstract PostsFragment contributePostsFragment();

}

package com.example.dagger2_java.di;

import androidx.lifecycle.ViewModelProvider;

import com.example.dagger2_java.viewmodels.ViewModelProviderFactory;

import dagger.Binds;
import dagger.Module;


@Module
public abstract class ViewModelFactoryModule {

    @Binds
    public abstract ViewModelProvider.Factory bindViewModelFactory(ViewModelProviderFactory viewModelProviderFactory);

    /* this way actually just like below :
        @Provides
        static ViewModelProvider.Factory bindViewModelFactory(ViewModelProviderFactory viewModelProviderFactory){
            return viewModelProviderFactory;
        }
      but if we're not to do anything else inside the method , we can just use @Bind , it's more efficient way to do this
    */

}

package com.example.dagger2_java.ui.auth;

import android.se.omapi.Session;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.LiveDataReactiveStreams;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.ViewModel;

import com.example.dagger2_java.SessionManager;
import com.example.dagger2_java.model.User;
import com.example.dagger2_java.network.auth.AuthApi;
import javax.inject.Inject;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;


public class AuthViewModel extends ViewModel {

    private static final String TAG = "AuthViewModel";

    //inject
    private final AuthApi authApi;
    private SessionManager sessionManager;

    // MediatorLiveData: 作為中間人的角色監聽其他LiveData
    private MediatorLiveData<AuthResource<User>> authUser = new MediatorLiveData<>();

    @Inject
    public AuthViewModel(AuthApi authApi,SessionManager sessionManager){
        this.authApi =  authApi;
        this.sessionManager = sessionManager;

        if(sessionManager!=null){
            Log.e(TAG,"sessionManager is working!");
        }else{
            Log.e(TAG,"sessionManager is null!");
        }
        Log.e(TAG,"AuthViewModel is working!");

    }

    public void fetchById(int userId){
       sessionManager.authenticateWithId(queryUserId(userId));
    }

    private LiveData<AuthResource<User>> queryUserId(int userId){
        return LiveDataReactiveStreams.fromPublisher(
                authApi.getUser(userId)
                        .subscribeOn(Schedulers.io())
                        .onErrorReturn(new Function<Throwable, User>() {
                            @Override
                            public User apply(@NonNull Throwable throwable) throws Exception {
                                Log.e(TAG,throwable.getMessage());
                                User errUser = new User();
                                errUser.setId(-1);
                                return errUser;
                            }
                        })
                        .map(new Function<User, AuthResource<User>>() {
                            @Override
                            public AuthResource<User> apply(@NonNull User user) throws Exception {
                                if(user.getId() == -1){
                                    return  AuthResource.error("FetchError",(User)null);
                                }
                                return AuthResource.authenticated(user);
                            }
                        })
        );
    }

    public LiveData<AuthResource<User>> observeAuthState(){
        return sessionManager.getAuthUser();
    }
}

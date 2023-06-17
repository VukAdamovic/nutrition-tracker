package com.example.myapplication.presentation.event;

import android.util.Log;

import androidx.lifecycle.ViewModel;

import com.example.myapplication.data.models.entities.UserEntity;
import com.example.myapplication.data.repositories.UserRepository;
import com.example.myapplication.presentation.contract.MainContract;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class MainViewModel extends ViewModel implements MainContract {

    private CompositeDisposable subscriptions = new CompositeDisposable();
    private UserRepository userRepository;

    @Inject
    public MainViewModel(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void getUserById(long id) {
        subscriptions.add(
                userRepository.getUserById(id)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                user -> Log.d("MainViewModel", "User: " + user.toString()),
                                throwable -> Log.e("MainViewModel", "Error: ", throwable)
                        )
        );
    }

    @Override
    public void getUserByUsernameAndPassword(String username, String password) {
        subscriptions.add(
                userRepository.getUserByUsernameAndPassword(username, password)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                user -> Log.d("MainViewModel", "User: " + user.toString()),
                                throwable -> Log.e("MainViewModel", "Error: ", throwable)
                        )
        );
    }

    @Override
    public void adduser(UserEntity userEntity) {
        subscriptions.add(
                userRepository.insertUser(userEntity)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                () -> Log.d("MainViewModel", "User added successfully"),
                                throwable -> Log.e("MainViewModel", "Error: ", throwable)
                        )
        );
    }

    @Override
    protected void onCleared() {
        subscriptions.clear();
        super.onCleared();
    }
}

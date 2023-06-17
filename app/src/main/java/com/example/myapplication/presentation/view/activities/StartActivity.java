package com.example.myapplication.presentation.view.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.myapplication.R;
import com.example.myapplication.application.MyApplication;
import com.example.myapplication.data.models.entities.UserEntity;
import com.example.myapplication.data.repositories.UserRepository;
import com.example.myapplication.presentation.event.MainViewModel;

import java.util.Objects;

public class StartActivity extends AppCompatActivity {

    public static final String KEY_ALREADY_LOGGED_IN = "alreadyLoggedIn";
    private MainViewModel mainViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Objects.requireNonNull(getSupportActionBar()).hide();
        setContentView(R.layout.activity_splash_screen);

//         Setup ViewModelProvider.Factory with Dagger
        ViewModelProvider.Factory factory = new ViewModelProvider.Factory() {
            @NonNull
            @Override
            public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
                if (modelClass.isAssignableFrom(MainViewModel.class)) {
                    UserRepository userRepository = ((MyApplication) getApplication()).getAppComponent().provideUserRepository();
                    return (T) new MainViewModel(userRepository);
                }
                throw new IllegalArgumentException("Unknown ViewModel class");
            }
        };

        mainViewModel = new ViewModelProvider(this, factory).get(MainViewModel.class);

        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            Intent intent = new Intent(StartActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        }, 2300);
        initObservers();
    }

    private void initObservers() {
        UserEntity user1 = new UserEntity(0, "mitar", "12345");
        UserEntity user2 = new UserEntity(0, "vuk", "12345");

        mainViewModel.adduser(user1);
        mainViewModel.adduser(user2);

        new Handler().postDelayed(() -> {
            long id = 1;
            mainViewModel.getUserById(id);
        }, 2000); // Delay fetching the user by 2 seconds
    }
}


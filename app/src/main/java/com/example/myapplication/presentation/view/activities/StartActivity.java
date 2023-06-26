package com.example.myapplication.presentation.view.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;
import com.example.myapplication.application.MyApplication;
import com.example.myapplication.databinding.ActivitySplashScreenBinding;
import com.example.myapplication.presentation.event.MainViewModel;

import java.util.Objects;

import javax.inject.Inject;

public class StartActivity extends AppCompatActivity {


    private ActivitySplashScreenBinding binding;

    private MainViewModel mainViewModel;

    @Inject
    public SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((MyApplication) getApplication()).getAppComponent().inject(this);
        binding = ActivitySplashScreenBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Objects.requireNonNull(getSupportActionBar()).hide();
        setContentView(R.layout.activity_splash_screen);

        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            boolean loggedIn= sharedPreferences.getBoolean("LoggedIn",false);
            if(loggedIn){
                Intent intent = new Intent(StartActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            } else {
                Intent intent = new Intent(StartActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        }, 2000);


//        mainViewModel.getMealsByCategory("Seafood");
//        new Handler(Looper.getMainLooper()).postDelayed(this::initObservers, 2300);

//        mainViewModel.getMealsLastSevenDays(1, currentDate);
//        mainViewModel.getMealsByCategory("Seafood");
//        mainViewModel.getMealsByName("Onion");
//        mainViewModel.getMealsByIngredient("chicken_breast");
//        mainViewModel.getMealById(52772);
//        mainViewModel.getIngredients("list");
//        mainViewModel.getEveryMeal()

    }
}
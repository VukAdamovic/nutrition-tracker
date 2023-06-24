package com.example.myapplication.presentation.view.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;
import com.example.myapplication.databinding.ActivitySplashScreenBinding;

import java.util.Objects;

public class StartActivity extends AppCompatActivity {

    public static final String KEY_ALREADY_LOGGED_IN = "alreadyLoggedIn";

    private ActivitySplashScreenBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySplashScreenBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Objects.requireNonNull(getSupportActionBar()).hide();
        setContentView(R.layout.activity_splash_screen);

        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            Intent intent = new Intent(StartActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
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
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
import com.example.myapplication.data.models.entities.MealEntity;
import com.example.myapplication.data.models.entities.UserEntity;
import com.example.myapplication.data.repositories.local.MealRepository;
import com.example.myapplication.data.repositories.local.UserRepository;
import com.example.myapplication.data.repositories.remote.CategoryRepository;
import com.example.myapplication.presentation.event.MainViewModel;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class StartActivity extends AppCompatActivity {

    public static final String KEY_ALREADY_LOGGED_IN = "alreadyLoggedIn";
    private MainViewModel mainViewModel;

    Date currentDate = new Date();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Objects.requireNonNull(getSupportActionBar()).hide();
        setContentView(R.layout.activity_splash_screen);

        ViewModelProvider.Factory factory = new ViewModelProvider.Factory() {
            @NonNull
            @Override
            public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
                if (modelClass.isAssignableFrom(MainViewModel.class)) {
                    UserRepository userRepository = ((MyApplication) getApplication()).getAppComponent().provideUserRepository();
                    MealRepository mealRepository = ((MyApplication) getApplication()).getAppComponent().provideMealRepository();
                    CategoryRepository categoryRepository = ((MyApplication) getApplication()).getAppComponent().provideCategoryRepository();
                    return (T) new MainViewModel(userRepository, mealRepository, categoryRepository);
                }
                throw new IllegalArgumentException("Unknown ViewModel class");
            }
        };

        mainViewModel = new ViewModelProvider(this, factory).get(MainViewModel.class);

//        new Handler(Looper.getMainLooper()).postDelayed(this::initObservers, 2300);

        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            Intent intent = new Intent(StartActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        }, 2300);

//        mainViewModel.getMealsLastSevenDays(1, currentDate);
        mainViewModel.getCategories();
    }

    private void initObservers() {
        mainViewModel.adduser(new UserEntity(0, "mitar", "12345"));
        mainViewModel.adduser(new UserEntity(0, "vuk", "12345"));



        List<String> ingredients1 = new ArrayList<>();
        ingredients1.add("Ingredient 1");
        ingredients1.add("Ingredient 2");

        MealEntity meal1 = new MealEntity(
                "Meal 1",
                "image_url_1",
                "Instructions for meal 1",
                "youtube_link_1",
                ingredients1,
                "Category 1",
                currentDate,
                200,
                1
        );

        MealEntity meal2 = new MealEntity(
                "Meal 2",
                "image_url_2",
                "Instructions for meal 2",
                "youtube_link_2",
                ingredients1,
                "Category 2",
                currentDate,
                300,
                1
        );

        MealEntity meal3 = new MealEntity(
                "Meal 3",
                "image_url_3",
                "Instructions for meal 3",
                "youtube_link_3",
                ingredients1,
                "Category 3",
                currentDate,
                300,
                1
        );

        mainViewModel.insertMeal(meal1);
        mainViewModel.insertMeal(meal2);
        mainViewModel.insertMeal(meal3);
    }
}


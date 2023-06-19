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
import com.example.myapplication.data.repositories.MealRepository;
import com.example.myapplication.data.repositories.UserRepository;
import com.example.myapplication.presentation.event.MainViewModel;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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
                    MealRepository mealRepository = ((MyApplication) getApplication()).getAppComponent().provideMealRepository();
                    return (T) new MainViewModel(userRepository, mealRepository);
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
        mainViewModel.adduser(new UserEntity(0, "mitar", "12345"));
        mainViewModel.adduser(new UserEntity(0, "vuk", "12345"));

        Date currentDate = new Date();

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

        mainViewModel.insertMeal(meal1);
        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            mainViewModel.deleteMeal(1);
        }, 5000);  // 5 seconds delay



        // Pretpostavka: Prikupite datum pripreme za prethodno ubačene objekte
//        Date currentDate = new Date();
//
//        mainViewModel.getAllMealsByDate(currentDate);

    }
}


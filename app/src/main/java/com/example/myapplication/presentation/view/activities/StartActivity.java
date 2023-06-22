package com.example.myapplication.presentation.view.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.myapplication.R;
import com.example.myapplication.application.MyApplication;
import com.example.myapplication.data.models.api.domain.Category;
import com.example.myapplication.data.models.api.domain.Ingredient;
import com.example.myapplication.data.models.api.domain.MealFiltered;
import com.example.myapplication.data.models.api.domain.MealSingle;
import com.example.myapplication.data.repositories.local.MealRepository;
import com.example.myapplication.data.repositories.local.UserRepository;
import com.example.myapplication.data.repositories.remote.calories.CalorieRepository;
import com.example.myapplication.data.repositories.remote.category.CategoryRepository;
import com.example.myapplication.data.repositories.remote.ingredient.IngredientRepository;
import com.example.myapplication.data.repositories.remote.meal.MealRepositoryRemote;
import com.example.myapplication.presentation.event.MainViewModel;

import java.util.Date;
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
                    MealRepositoryRemote mealRepositoryRemote = ((MyApplication) getApplication()).getAppComponent().provideMealRepositoryRemote();
                    IngredientRepository ingredientRepository = ((MyApplication) getApplication()).getAppComponent().provideIngredientRepository();
                    CalorieRepository calorieRepository = ((MyApplication) getApplication()).getAppComponent().provideCalorieRepository();
                    return (T) new MainViewModel(userRepository, mealRepository, categoryRepository, mealRepositoryRemote, ingredientRepository, calorieRepository);
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

//        mainViewModel.getCategories();
//        mainViewModel.getMealsByCategory("Seafood");
//        mainViewModel.getMealsByName("Arrabiata");
//        mainViewModel.getMealsByIngredient("chicken_breast");
//        mainViewModel.getMealById(52772);
//        mainViewModel.getIngredients("list");

        mainViewModel.getEveryMeal();







        initObservers();







    }


    private void initObservers() {
        mainViewModel.getAllCategories().observe(this, categories -> {
            for (Category category : categories) {
                Log.d("MainViewModel", "Category ID: " + category.getId());
                Log.d("MainViewModel", "Category Name: " + category.getName());
                Log.d("MainViewModel", "Category Thumbnail: " + category.getThumbnail());
                Log.d("MainViewModel", "Category Description: " + category.getDescription());
            }
        });

        mainViewModel.getAllFilteredMealsByCategory().observe(this, meals -> {
            for (MealFiltered meal : meals) {
                Log.d("MainViewModel", "Meal ID: " + meal.getId());
                Log.d("MainViewModel", "Meal Thumbnail: " + meal.getThumbnail());
                Log.d("MainViewModel", "Meal Name: " + meal.getName());
            }
        });

        mainViewModel.getAllMealsByName().observe(this, meals -> {
            for (MealSingle meal : meals) {
                Log.d("MainViewModel", "Meal ID: " + meal.getId());
                Log.d("MainViewModel", "Meal Name: " + meal.getMealName());
                Log.d("MainViewModel", "Meal Image URL: " + meal.getMealImageUrl());
                Log.d("MainViewModel", "Instructions: " + meal.getInstructions());
                Log.d("MainViewModel", "YouTube Link: " + meal.getYouTubeLink());
                Log.d("MainViewModel", "Ingredients Measurements: " + meal.getIngredientsMeasurements());
                Log.d("MainViewModel", "Category: " + meal.getCategory());
                Log.d("MainViewModel", "Area: " + meal.getArea());
                Log.d("MainViewModel", "Tags: " + meal.getTags());
                Log.d("MainViewModel", "Calories: " + meal.getCalories());
            }
        });

        mainViewModel.getAllFilteredMealsByIngredient().observe(this, meals -> {
            for (MealFiltered meal : meals) {
                Log.d("MainViewModel", "Meal ID: " + meal.getId());
                Log.d("MainViewModel", "Meal Thumbnail: " + meal.getThumbnail());
                Log.d("MainViewModel", "Meal Name: " + meal.getName());
            }
        });

        mainViewModel.getAllMeals().observe(this, meals -> {
            for (MealSingle meal : meals) {
                Log.d("MainViewModel", "Meal ID: " + meal.getId());
                Log.d("MainViewModel", "Meal Name: " + meal.getMealName());
                Log.d("MainViewModel", "Meal Image URL: " + meal.getMealImageUrl());
                Log.d("MainViewModel", "Instructions: " + meal.getInstructions());
                Log.d("MainViewModel", "YouTube Link: " + meal.getYouTubeLink());
                Log.d("MainViewModel", "Ingredients Measurements: " + meal.getIngredientsMeasurements());
                Log.d("MainViewModel", "Category: " + meal.getCategory());
                Log.d("MainViewModel", "Area: " + meal.getArea());
                Log.d("MainViewModel", "Tags: " + meal.getTags());
                Log.d("MainViewModel", "Calories: " + meal.getCalories());
            }
        });

        mainViewModel.getSingleMealById().observe(this, meals -> {
            for (MealSingle meal : meals) {
                Log.d("MainViewModel", "Meal ID: " + meal.getId());
                Log.d("MainViewModel", "Meal Name: " + meal.getMealName());
                Log.d("MainViewModel", "Meal Image URL: " + meal.getMealImageUrl());
                Log.d("MainViewModel", "Instructions: " + meal.getInstructions());
                Log.d("MainViewModel", "YouTube Link: " + meal.getYouTubeLink());
                Log.d("MainViewModel", "Ingredients Measurements: " + meal.getIngredientsMeasurements());
                Log.d("MainViewModel", "Category: " + meal.getCategory());
                Log.d("MainViewModel", "Area: " + meal.getArea());
                Log.d("MainViewModel", "Tags: " + meal.getTags());
                Log.d("MainViewModel", "Calories: " + meal.getCalories());
            }
        });

        mainViewModel.getAllIngredients().observe(this, ingredients -> {
            for (Ingredient ingredient : ingredients) {
                Log.d("MainViewModel", "Ingredient ID: " + ingredient.getId());
                Log.d("MainViewModel", "Ingredient Name: " + ingredient.getName());
                Log.d("MainViewModel", "Ingredient Description: " + ingredient.getDescription());
            }
        });


    }

//    private void initObservers() {
//        mainViewModel.adduser(new UserEntity(0, "mitar", "12345"));
//        mainViewModel.adduser(new UserEntity(0, "vuk", "12345"));
//
//
//
//        List<String> ingredients1 = new ArrayList<>();
//        ingredients1.add("Ingredient 1");
//        ingredients1.add("Ingredient 2");
//
//        MealEntity meal1 = new MealEntity(
//                "Meal 1",
//                "image_url_1",
//                "Instructions for meal 1",
//                "youtube_link_1",
//                ingredients1,
//                "Category 1",
//                currentDate,
//                200,
//                1
//        );
//
//        MealEntity meal2 = new MealEntity(
//                "Meal 2",
//                "image_url_2",
//                "Instructions for meal 2",
//                "youtube_link_2",
//                ingredients1,
//                "Category 2",
//                currentDate,
//                300,
//                1
//        );
//
//        MealEntity meal3 = new MealEntity(
//                "Meal 3",
//                "image_url_3",
//                "Instructions for meal 3",
//                "youtube_link_3",
//                ingredients1,
//                "Category 3",
//                currentDate,
//                300,
//                1
//        );
//
//        mainViewModel.insertMeal(meal1);
//        mainViewModel.insertMeal(meal2);
//        mainViewModel.insertMeal(meal3);
//    }
}


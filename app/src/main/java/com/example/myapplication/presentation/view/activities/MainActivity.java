package com.example.myapplication.presentation.view.activities;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.myapplication.R;
import com.example.myapplication.application.MyApplication;
import com.example.myapplication.data.models.api.domain.Category;
import com.example.myapplication.data.models.api.domain.Ingredient;
import com.example.myapplication.data.models.api.domain.MealFiltered;
import com.example.myapplication.data.models.api.domain.MealSingle;
import com.example.myapplication.data.models.entities.UserEntity;
import com.example.myapplication.data.repositories.local.MealRepository;
import com.example.myapplication.data.repositories.local.UserRepository;
import com.example.myapplication.data.repositories.remote.calories.CalorieRepository;
import com.example.myapplication.data.repositories.remote.category.CategoryRepository;
import com.example.myapplication.data.repositories.remote.ingredient.IngredientRepository;
import com.example.myapplication.data.repositories.remote.meal.MealRepositoryRemote;
import com.example.myapplication.databinding.ActivityMainBinding;
import com.example.myapplication.presentation.event.MainViewModel;
import com.example.myapplication.presentation.view.fragments.ChartFragment;
import com.example.myapplication.presentation.view.fragments.FilterFragment;
import com.example.myapplication.presentation.view.fragments.HomeFragment;
import com.example.myapplication.presentation.view.fragments.PlanFragment;
import com.example.myapplication.presentation.view.fragments.SettingsFragment;

import java.util.List;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    public static MainViewModel mainViewModel;
    private ActivityMainBinding binding;

    //Api
    public static MutableLiveData<List<Category>> allCategoriesLiveData;
    public static MutableLiveData<List<MealFiltered>> allFilteredMealsByCategoryLiveData;
    public static MutableLiveData<List<MealFiltered>> allFilteredMealsByIngredientLiveData;
    public static MutableLiveData<List<MealSingle>> allMealsByNameLiveData;
    public static MutableLiveData<List<MealSingle>> singleMealByIdLiveData;
    public static MutableLiveData<MealSingle> currentMealWithCaloriesLiveData;
    public static MutableLiveData<List<MealSingle>> allMealsLiveData;
    public static MutableLiveData<List<Ingredient>> allIngredientsLiveData;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        initViewModel();
        setContentView(binding.getRoot());
        Objects.requireNonNull(getSupportActionBar()).hide();
        replaceFragment(new HomeFragment());


        binding.bottomNavigationView.setOnItemSelectedListener(item -> {
            if (item.getItemId() == R.id.home) {
                replaceFragment(new HomeFragment());
            } else if (item.getItemId() == R.id.filter) {
                replaceFragment(new FilterFragment());
            } else if (item.getItemId() == R.id.statistic) {
                replaceFragment(new ChartFragment());
            } else if (item.getItemId() == R.id.plan) {
                replaceFragment(new PlanFragment());
            } else if (item.getItemId() == R.id.settings) {
                replaceFragment(new SettingsFragment());
            }
            return true;
        });
    }

    private void initViewModel() {
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

        initObservers();
    }

    private void initObservers() {
        allCategoriesLiveData = mainViewModel.getAllCategories();
        allFilteredMealsByCategoryLiveData = mainViewModel.getAllFilteredMealsByCategory();
        allFilteredMealsByIngredientLiveData = mainViewModel.getAllFilteredMealsByIngredient();
        allMealsByNameLiveData = mainViewModel.getAllMealsByName();
        singleMealByIdLiveData = mainViewModel.getSingleMealById();
        currentMealWithCaloriesLiveData = mainViewModel.getCurrentMealWithCalories();
        allMealsLiveData = mainViewModel.getAllMeals();
        allIngredientsLiveData = mainViewModel.getAllIngredients();
    }


    private void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout, fragment);
        fragmentTransaction.commit();
    }
}

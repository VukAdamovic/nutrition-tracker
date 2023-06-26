package com.example.myapplication.presentation.view.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.myapplication.R;
import com.example.myapplication.application.MyApplication;
import com.example.myapplication.data.models.entities.UserEntity;
import com.example.myapplication.data.repositories.local.MealRepository;
import com.example.myapplication.data.repositories.local.UserRepository;
import com.example.myapplication.data.repositories.remote.calories.CalorieRepository;
import com.example.myapplication.data.repositories.remote.category.CategoryRepository;
import com.example.myapplication.data.repositories.remote.ingredient.IngredientRepository;
import com.example.myapplication.data.repositories.remote.meal.MealRepositoryRemote;
import com.example.myapplication.databinding.ActivityLoginBinding;
import com.example.myapplication.databinding.ActivityMainBinding;
import com.example.myapplication.presentation.event.MainViewModel;

import java.util.Objects;

import javax.inject.Inject;

public class LoginActivity extends AppCompatActivity {

    private ActivityLoginBinding binding;

    private TextView error;

    public  static MainViewModel mainViewModel;

    public static MutableLiveData<UserEntity> activeUser;

    @Inject
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ((MyApplication) getApplication()).getAppComponent().inject(this);

        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        initViewModel();
        setContentView(binding.getRoot());
        Objects.requireNonNull(getSupportActionBar()).hide();
        initListeners();
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

    private void initListeners(){
        initObservers();
        error = binding.textView51;

        Button loginBtn = binding.button;
        loginBtn.setOnClickListener( v -> {
            String username = binding.editTextText.getText().toString();
            String password = binding.editTextTextPassword.getText().toString();
            if(username.equals("") || password.equals("")){
                error.setText("All fields are required");
                error.setVisibility(View.VISIBLE);
            } else {
                mainViewModel.getUserByUsernameAndPassword(username, password);
            }

        });
    }


    private void initObservers(){
        activeUser = mainViewModel.getActiveUser();
        activeUser.observe(this, userEntity -> {
            if(userEntity != null){
                error.setVisibility(View.INVISIBLE);
                sharedPreferences.edit().putInt("USER_ID", userEntity.getId()).apply();
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }else{
                error.setText("Invalid Username or Password");
                error.setVisibility(View.VISIBLE);
            }
        });
    }

}

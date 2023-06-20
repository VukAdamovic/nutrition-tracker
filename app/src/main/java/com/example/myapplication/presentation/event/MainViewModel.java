package com.example.myapplication.presentation.event;

import android.util.Log;

import androidx.lifecycle.ViewModel;

import com.example.myapplication.data.models.api.domain.Category;
import com.example.myapplication.data.models.api.domain.MealByCategory;
import com.example.myapplication.data.models.entities.MealEntity;
import com.example.myapplication.data.models.entities.UserEntity;
import com.example.myapplication.data.repositories.local.MealRepository;
import com.example.myapplication.data.repositories.local.UserRepository;
import com.example.myapplication.data.repositories.remote.category.CategoryRepository;
import com.example.myapplication.data.repositories.remote.meal.MealRepositoryRemote;
import com.example.myapplication.presentation.contract.MainContract;

import java.util.Calendar;
import java.util.Date;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class MainViewModel extends ViewModel implements MainContract {

    private CompositeDisposable subscriptions = new CompositeDisposable();
    private UserRepository userRepository;
    private MealRepository mealRepository;
    private CategoryRepository categoryRepository;
    private MealRepositoryRemote mealRepositoryRemote;

    @Inject
    public MainViewModel(UserRepository userRepository, MealRepository mealRepository, CategoryRepository categoryRepository, MealRepositoryRemote mealRepositoryRemote) {
        this.userRepository = userRepository;
        this.mealRepository = mealRepository;
        this.categoryRepository = categoryRepository;
        this.mealRepositoryRemote = mealRepositoryRemote;
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
    public void insertMeal(MealEntity mealEntity) {
        subscriptions.add(
                mealRepository.insertMeal(mealEntity)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                () -> Log.d("MainViewModel New Meal", "Meal added successfully"),
                                throwable -> Log.e("MainViewModel New Meal", "Error: ", throwable)
                        )
        );

    }

    @Override
    public void updateMeal(int id, String mealImageUrl, Date preparationDate) {
        subscriptions.add(
                mealRepository.updateMeal(id, mealImageUrl, preparationDate)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                () -> Log.d("MainViewModel Update Meal", "Meal updated successfully"),
                                throwable -> Log.e("MainViewModel Update Meal", "Error: ", throwable)
                        )
        );
    }

    @Override
    public void deleteMeal(int id) {
        subscriptions.add(
                mealRepository.deleteMeal(id)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                () -> Log.d("MainViewModel Delete Meal", "Meal deleted successfully"),
                                throwable -> Log.e("MainViewModel Delete Meal", "Error: ", throwable)
                        )
        );
    }

    @Override
    public void getMealsByUserId(int userId) {
        subscriptions.add(
                mealRepository.getMealsByUserId(userId)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                meals -> {
                                    for (MealEntity meal : meals) {
                                        Log.d("MainViewModel Meal", "Meal: " + meal.getMealName());
                                    }
                                },
                                throwable -> {
                                    Log.e("MainViewModel Meals", "Error: ", throwable);
                                }
                        )
        );
    }

    @Override
    public void getMealsLastSevenDays(int userId, Date currentDate) {

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(currentDate);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        Long currentTimestamp = calendar.getTimeInMillis();

        calendar.add(Calendar.DAY_OF_YEAR, -7);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 1);
        Long sevenDaysAgoTimestamp = calendar.getTimeInMillis();

        subscriptions.add(
                mealRepository.getMealsLastSevenDays(userId, currentTimestamp, sevenDaysAgoTimestamp)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                meals -> {
                                    for (MealEntity meal : meals) {
                                        Log.d("MainViewModel Meal", "Meal: " + meal.getMealName());
                                    }
                                },
                                throwable -> {
                                    Log.e("MainViewModel Meals", "Error: ", throwable);
                                }
                        )
        );
    }

    @Override
    public void getCategories() {
        subscriptions.add(
                categoryRepository.getAllCategories()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .doOnComplete(() -> Log.d("MainViewModel", "Fetch Complete"))
                        .subscribe(
                                categories -> {
                                    for (Category category : categories) {
                                        Log.d("MainViewModel", "Category: " + category.getName());
                                    }
                                },
                                throwable -> Log.e("MainViewModel", "Error: ", throwable)
                        )
        );
    }

    @Override
    public void getMealsByCategory(String category) {
        subscriptions.add(
                mealRepositoryRemote.getAllMealsByCategory(category)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .doOnComplete(() -> Log.d("MainViewModel", "Fetch Complete"))
                        .subscribe(
                                meals -> {
                                    Log.d("MainViewModel", "Meal: " + meals.size());
//                                    for (MealByCategory meal : meals) {
//                                        Log.d("MainViewModel", "Meal: " + meal.getName());
//                                    }
                                },
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

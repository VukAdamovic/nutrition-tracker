package com.example.myapplication.presentation.contract;

import com.example.myapplication.data.models.entities.MealEntity;
import com.example.myapplication.data.models.entities.UserEntity;

import java.util.Date;


public interface MainContract {
    void getUserById(long id);
    void getUserByUsernameAndPassword(String username, String password);
    void adduser(UserEntity userEntity);
    void insertMeal(MealEntity mealEntity);
    void updateMeal(int id, String mealImageUrl, Date preparationDate);
    void deleteMeal(int id);
    void getMealsByUserId(int userId);
    void getMealsLastSevenDays(int userId, Date currentDate);
}

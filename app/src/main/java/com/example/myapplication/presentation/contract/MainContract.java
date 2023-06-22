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

    // Category api calls


    void getCategories();
    void getMealsByCategory(String category);

    void getMealsByName(String mealName);

    void getMealsByIngredient(String ingredientName);

    void getEveryMeal();  //namerno sam je nazavo ovako jer se buni kad nazovem getAllMeal nzm sto

    void getMealById(int id);

    void getIngredients(String s);
}

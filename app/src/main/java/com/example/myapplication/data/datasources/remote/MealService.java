package com.example.myapplication.data.datasources.remote;

import com.example.myapplication.data.models.api.meal.AllMealsResponse;
import com.example.myapplication.data.models.api.meal_filtered.AllMealsFilteredResponse;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MealService {

    @GET("filter.php")
    Observable<AllMealsFilteredResponse> getAllMealsByCategory(@Query("c") String category);

    @GET("search.php")
    Observable<AllMealsResponse> getMealByName(@Query("s") String mealName);

    @GET("filter.php")
    Observable<AllMealsFilteredResponse> getMealsByIngredient(@Query("i") String ingredientName);





}

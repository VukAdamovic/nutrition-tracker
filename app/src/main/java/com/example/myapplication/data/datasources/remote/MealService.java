package com.example.myapplication.data.datasources.remote;

import com.example.myapplication.data.models.api.meal.AllMealsResponse;
import com.example.myapplication.data.models.api.meal_by_category.AllMealsByCategoryResponse;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MealService {

    @GET("filter.php")
    Observable<AllMealsByCategoryResponse> getAllMealsByCategory(@Query("c") String category);

    @GET("search.php")
    Observable<AllMealsResponse> getMealByName(@Query("s") String mealName);

}

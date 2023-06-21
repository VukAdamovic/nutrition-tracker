package com.example.myapplication.data.datasources.remote;

import com.example.myapplication.data.models.api.meal_filtered.AllMealsFilteredResponse;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface IngredientService {

    @GET("list.php")
    Observable<AllMealsFilteredResponse> getAllIngredients(@Query("i") String s);
}

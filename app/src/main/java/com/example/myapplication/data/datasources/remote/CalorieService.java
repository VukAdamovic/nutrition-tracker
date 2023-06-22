package com.example.myapplication.data.datasources.remote;

import com.example.myapplication.data.models.api.calories.CalorieResponse;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface CalorieService {

    @GET("nutrition")
    Observable<CalorieResponse> getCaloriesForMeal(@Query("query") String query);
}

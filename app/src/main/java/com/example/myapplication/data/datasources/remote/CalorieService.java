package com.example.myapplication.data.datasources.remote;

import com.example.myapplication.data.models.api.calories.CalorieResponse;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface CalorieService {

    @GET("nutrition")
    Observable<List<CalorieResponse>> getCaloriesForMeal(@Query("query") String query);
}

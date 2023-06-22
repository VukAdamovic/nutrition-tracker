package com.example.myapplication.data.datasources.remote;

import com.example.myapplication.data.models.api.calories.AllCaloriesResponse;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface CalorieService {

    @GET("nutrition")
    Observable<AllCaloriesResponse> getCaloriesForMeal(@Query("query") String query);
}

package com.example.myapplication.data.repositories.remote.calories;

import android.util.Log;

import com.example.myapplication.data.datasources.remote.CalorieService;
import com.example.myapplication.data.models.api.calories.CalorieResponse;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.functions.Function;

public class CalorieRepositoryImpl implements CalorieRepository {
    private CalorieService calorieService;

    @Inject
    public CalorieRepositoryImpl(CalorieService calorieService) {
        this.calorieService = calorieService;
    }

    @Override
    public Observable<Double> getCaloriesForMeal(String query) {
        Log.d("CalorieRepositoryImpl", "Query: " + query);
        return calorieService
                .getCaloriesForMeal(query)
                .map(new Function<List<CalorieResponse>, Double>() {
                    @Override
                    public Double apply(List<CalorieResponse> calorieResponses) throws Exception {
                        if (!calorieResponses.isEmpty()) {
                            return Double.parseDouble(calorieResponses.get(0).getCalories());
                        } else {
                            return 0.0;
                        }
                    }
                });
    }


}


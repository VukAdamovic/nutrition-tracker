package com.example.myapplication.data.repositories.remote.calories;

import com.example.myapplication.data.datasources.remote.CalorieService;
import com.example.myapplication.data.models.api.calories.CalorieResponse;

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
        return calorieService
                .getCaloriesForMeal(query)
                .map(new Function<CalorieResponse, Double>() {
                    @Override
                    public Double apply(CalorieResponse calorieResponse) throws Exception {
                        return Double.parseDouble(calorieResponse.getCalories);
                    }
                });
    }
}

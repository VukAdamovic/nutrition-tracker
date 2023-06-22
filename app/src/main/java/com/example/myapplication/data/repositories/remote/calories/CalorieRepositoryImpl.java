package com.example.myapplication.data.repositories.remote.calories;

import com.example.myapplication.data.datasources.remote.CalorieService;
import com.example.myapplication.data.models.api.calories.AllCaloriesResponse;
import com.example.myapplication.data.models.api.calories.CalorieResponse;

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
        return calorieService
                .getCaloriesForMeal(query)
                .map(new Function<AllCaloriesResponse, Double>() {
                    @Override
                    public Double apply(AllCaloriesResponse allCaloriesResponse) throws Exception {
                        List<CalorieResponse> items = allCaloriesResponse.getItems();
                        if (items != null && !items.isEmpty()) {
                            CalorieResponse calorieResponse = items.get(0);
                            return Double.parseDouble(calorieResponse.getCalories());
                        } else {
                            throw new Exception("Invalid response format: empty items");
                        }
                    }
                });
    }
}


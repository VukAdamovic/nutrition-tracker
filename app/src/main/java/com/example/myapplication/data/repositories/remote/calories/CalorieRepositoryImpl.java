package com.example.myapplication.data.repositories.remote.calories;

import android.util.Log;

import com.example.myapplication.data.datasources.remote.CalorieService;
import com.example.myapplication.data.models.api.calories.CalorieResponse;
import com.example.myapplication.data.models.api.domain.MealSingle;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

public class CalorieRepositoryImpl implements CalorieRepository {

    private CalorieService calorieService;

    @Inject
    public CalorieRepositoryImpl(CalorieService calorieService) {
        this.calorieService = calorieService;
    }

    @Override
    public Observable<Double> getCaloriesForMeal(String ingredient) {
        return calorieService
                .getCaloriesForMeal(ingredient)
                .map(new Function<List<CalorieResponse>, Double>() {
                    @Override
                    public Double apply(List<CalorieResponse> calorieResponses) {
                        if (calorieResponses != null && !calorieResponses.isEmpty()) {
                            double total = 0.0;
                            for (CalorieResponse calorieResponse : calorieResponses) {
                                total += Double.parseDouble(calorieResponse.getCalories());
                                Log.d("CalorieRepository", "Name: " + calorieResponse.getName() + "Calories: " + calorieResponse.getCalories());
                            }
                            return total;
                        } else {
                            return 0.0;
                        }
                    }
                });
    }
}


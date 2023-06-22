package com.example.myapplication.data.repositories.remote.calories;

import io.reactivex.Observable;

public interface CalorieRepository {

    Observable<Double> getCaloriesForMeal(String query);
}

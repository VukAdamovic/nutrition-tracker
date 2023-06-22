package com.example.myapplication.data.repositories.remote.calories;

import io.reactivex.Observable;

public interface CaloriesRepository {

    Observable<Integer> getCaloriesForMeal();
}

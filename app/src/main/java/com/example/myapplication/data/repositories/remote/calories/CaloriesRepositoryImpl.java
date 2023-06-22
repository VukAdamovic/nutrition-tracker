package com.example.myapplication.data.repositories.remote.calories;

import com.example.myapplication.data.datasources.remote.CaloriesService;

import javax.inject.Inject;

import io.reactivex.Observable;

public class CaloriesRepositoryImpl implements CaloriesRepository{

    private CaloriesService caloriesService;

    @Inject
    public CaloriesRepositoryImpl(CaloriesService caloriesService) {
        this.caloriesService = caloriesService;
    }

    @Override
    public Observable<Integer> getCaloriesForMeal() {
        return null;
    }
}

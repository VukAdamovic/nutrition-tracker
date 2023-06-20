package com.example.myapplication.data.repositories.remote.meal;

import com.example.myapplication.data.models.api.domain.MealByCategory;

import java.util.List;

import io.reactivex.Observable;

public interface MealRepositoryRemote {

    Observable<List<MealByCategory>> getAllMealsByCategory(String category);
}

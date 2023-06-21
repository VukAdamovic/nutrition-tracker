package com.example.myapplication.data.repositories.remote.meal;

import com.example.myapplication.data.models.api.domain.MealByCategory;
import com.example.myapplication.data.models.api.domain.MealSingle;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Query;

public interface MealRepositoryRemote {

    Observable<List<MealByCategory>> getAllMealsByCategory(String category);

    Observable<List<MealSingle>> getMealsByName(@Query("s") String mealName);
}

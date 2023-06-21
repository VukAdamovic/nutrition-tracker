package com.example.myapplication.data.repositories.remote.meal;

import com.example.myapplication.data.models.api.domain.MealFiltered;
import com.example.myapplication.data.models.api.domain.MealSingle;

import java.util.List;

import io.reactivex.Observable;

public interface MealRepositoryRemote {

    Observable<List<MealFiltered>> getAllMealsByCategory(String category);

    Observable<List<MealSingle>> getMealsByName(String mealName);

    Observable<List<MealFiltered>> getMealsByIngredient(String ingredientName);



}

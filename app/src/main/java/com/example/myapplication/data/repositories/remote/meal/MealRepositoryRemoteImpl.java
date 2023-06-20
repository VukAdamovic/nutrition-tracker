package com.example.myapplication.data.repositories.remote.meal;

import android.util.Log;

import com.example.myapplication.data.datasources.remote.MealService;
import com.example.myapplication.data.models.api.domain.MealByCategory;
import com.example.myapplication.data.models.api.meal_by_category.AllMealsByCategoryResponse;
import com.example.myapplication.data.models.api.meal_by_category.MealByCategoryResponse;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.functions.Function;

public class MealRepositoryRemoteImpl implements MealRepositoryRemote {

    private MealService mealService;

    @Inject
    public MealRepositoryRemoteImpl(MealService mealService) {
        this.mealService = mealService;
    }

    @Override
    public Observable<List<MealByCategory>> getAllMealsByCategory(String category) {
        return mealService
                .getAllMealsByCategory(category)
                .map(new Function<AllMealsByCategoryResponse, List<MealByCategory>>() {
                    @Override
                    public List<MealByCategory> apply(AllMealsByCategoryResponse allMealsByCategoryResponse) throws Exception {
                        List<MealByCategory> meals = new ArrayList<>();
                        if(allMealsByCategoryResponse != null && allMealsByCategoryResponse.getAllMeals() != null){
                            for (MealByCategoryResponse mealByCategoryResponse : allMealsByCategoryResponse.getAllMeals()) {
                                meals.add(new MealByCategory(
                                        mealByCategoryResponse.getId(),
                                        mealByCategoryResponse.getThumbnail(),
                                        mealByCategoryResponse.getName()
                                ));
                            }
                        }
                        return meals;
                    }
                });
    }
}

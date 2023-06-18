package com.example.myapplication.data.repositories;

import com.example.myapplication.data.models.entities.MealEntity;

import java.util.Date;
import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Observable;


public interface MealRepository {

    Observable<List<MealEntity>> getAllMealsByDate(Long preparationDate);

    Completable insertMeal(MealEntity mealEntity);

    Completable updateMeal(int id, String mealImageUrl, Date preparationDate);

    Completable deleteMeal(int id);
}

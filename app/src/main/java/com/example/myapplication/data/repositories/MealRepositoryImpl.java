package com.example.myapplication.data.repositories;

import com.example.myapplication.data.datasources.MealDao;
import com.example.myapplication.data.models.entities.MealEntity;

import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Single;

public class MealRepositoryImpl implements MealRepository{

    private final MealDao mealDao;

    @Inject
    public MealRepositoryImpl(MealDao mealDao){
        this.mealDao = mealDao;
    }


    @Override
    public Observable<List<MealEntity>> getAllMealsByDate(Long preparationDate) {
        return this.mealDao.getAllMealsByDate(preparationDate);
    }

    @Override
    public Completable insertMeal(MealEntity mealEntity) {
        return this.mealDao.insertMeal(mealEntity);
    }

    @Override
    public Completable updateMeal(int id, String mealImageUrl, Date preparationDate) {
        return Completable.fromCallable(() -> {
            this.mealDao.getMealByIdAndUpdate(id,mealImageUrl,preparationDate);
            return Completable.complete();
        });
    }

    @Override
    public Completable deleteMeal(int id) {
        return Completable.fromCallable(() -> {
            this.mealDao.getMealByIdAndDelete(id);
            return Completable.complete();
        });
    }


}

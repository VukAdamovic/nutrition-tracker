package com.example.myapplication.data.repositories;

import com.example.myapplication.data.datasources.MealDao;
import com.example.myapplication.data.models.entities.MealEntity;

import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

public class MealRepositoryImpl implements MealRepository{

    private final MealDao mealDao;

    @Inject
    public MealRepositoryImpl(MealDao mealDao){
        this.mealDao = mealDao;
    }

    @Override
    public Completable insertMeal(MealEntity mealEntity) {
        return this.mealDao.insertMeal(mealEntity);
    }

    @Override
    public Completable updateMeal(int id, String mealImageUrl, Date preparationDate) {
        return Completable.fromAction(() -> {
                    MealEntity mealEntity = this.mealDao.getMealById(id);
                    mealEntity.setMealImageUrl(mealImageUrl);
                    mealEntity.setPreparationDate(preparationDate);
                    this.mealDao.updateMeal(mealEntity).subscribe();
                })
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Completable deleteMeal(int id) {
        return Completable.fromAction(() -> {
                    MealEntity mealEntity = this.mealDao.getMealById(id);
                    this.mealDao.deleteMeal(mealEntity).subscribe();
                })
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Observable<List<MealEntity>> getMealsByUserId(int userId) {
        return this.mealDao.getMealsByUserId(userId);
    }

    @Override
    public Observable<List<MealEntity>> getMealsLastSevenDays(int userId, Long currentDate, Long sevenDaysAgo) {
        return this.mealDao.getMealsLastSevenDays(userId, currentDate, sevenDaysAgo);
    }

}

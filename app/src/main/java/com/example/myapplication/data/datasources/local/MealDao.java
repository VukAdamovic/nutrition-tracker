package com.example.myapplication.data.datasources.local;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.myapplication.data.models.entities.MealEntity;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Observable;

@Dao
public  abstract class MealDao {


    // pretraga jela - neka bude po datumu
    @Query("SELECT * FROM meals WHERE user_id = :userId AND preparationDate BETWEEN :sevenDaysAgo AND :currentDate")
    public abstract Observable<List<MealEntity>> getMealsLastSevenDays(int userId, Long currentDate, Long sevenDaysAgo);

    // dohvati jelo po id - ovo mi treba za update, delete
    @Query("SELECT * FROM meals WHERE id == :id")
    public abstract MealEntity getMealById(int id);

    @Query("SELECT * FROM meals WHERE user_id == :userId")
    public abstract Observable<List<MealEntity>> getMealsByUserId(int userId);

    // dodavanje jela u meni - CUVANJE
    @Insert
    public abstract Completable insertMeal(MealEntity mealEntity);

    // updatovanje jela u meni - Moze samo da se updajetuje slika i datum pripreme
    @Update
    public abstract Completable updateMeal(MealEntity mealEntity);

    // brisanje jela iz menija
    @Delete
    public abstract Completable deleteMeal(MealEntity mealEntity);
}

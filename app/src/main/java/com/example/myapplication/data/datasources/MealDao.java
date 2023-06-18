package com.example.myapplication.data.datasources;

import android.util.Log;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import com.example.myapplication.data.models.entities.MealEntity;

import java.util.Date;
import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Observable;

@Dao
public  abstract class MealDao {


    // pretraga jela - neka bude po datumu
    @Query("SELECT * FROM meals WHERE preparationDate == :preparationDate")
    public abstract Observable<List<MealEntity>> getAllMealsByDate(Long preparationDate);


    // dohvati jelo po id - ovo mi treba za update, delete
    @Query("SELECT * FROM meals WHERE id == :id")
    public abstract MealEntity getMealById(int id);

    // dodavanje jela u meni - CUVANJE
    @Insert
    public abstract Completable insertMeal(MealEntity mealEntity);

    // updatovanje jela u meni - Moze samo da se updajetuje slika i datum pripreme
    @Update
    public abstract Completable updateMeal(MealEntity mealEntity);

    // brisanje jela iz menija
    @Delete
    public abstract Completable deleteMeal(MealEntity mealEntity);


    //Iz nekog razloga Lombok ne radi
    @Transaction
    public void getMealByIdAndUpdate(int id, String mealImageUrl, Date preparationDate) {
        MealEntity mealEntity = getMealById(id);
//        mealEntity.setMealImageUrl(mealImageUrl);
//        mealEntity.setPreparationDate(preparationDate);
        updateMeal(mealEntity);

    }

    @Transaction
    public void getMealByIdAndDelete(int id) {
        MealEntity mealEntity = getMealById(id);
        deleteMeal(mealEntity);
    }
}

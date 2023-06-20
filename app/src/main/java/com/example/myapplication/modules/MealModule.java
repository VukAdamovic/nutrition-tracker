package com.example.myapplication.modules;

import com.example.myapplication.data.datasources.local.MealDao;
import com.example.myapplication.data.db.MyDatabase;
import com.example.myapplication.data.repositories.MealRepository;
import com.example.myapplication.data.repositories.MealRepositoryImpl;

import dagger.Module;
import dagger.Provides;

@Module
public class MealModule {

    @Provides
    public MealRepository provideMealRepository(MealDao mealDao){
        return new MealRepositoryImpl(mealDao);
    }

    @Provides
    public MealDao provideMealDao(MyDatabase myDatabase){
        return myDatabase.getMealDao();
    }
}

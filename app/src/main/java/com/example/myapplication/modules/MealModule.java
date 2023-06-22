package com.example.myapplication.modules;

import com.example.myapplication.data.datasources.local.MealDao;
import com.example.myapplication.data.datasources.remote.MealService;
import com.example.myapplication.data.db.MyDatabase;
import com.example.myapplication.data.repositories.local.MealRepository;
import com.example.myapplication.data.repositories.local.MealRepositoryImpl;
import com.example.myapplication.data.repositories.remote.meal.MealRepositoryRemote;
import com.example.myapplication.data.repositories.remote.meal.MealRepositoryRemoteImpl;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

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

    @Provides
    public MealRepositoryRemote provideMealRepositoryRemote(MealService mealService) {
        return new MealRepositoryRemoteImpl(mealService);
    }

    @Provides
    public MealService provideMealService(@Named("mealdb")Retrofit retrofit) {
        return retrofit.create(MealService.class);
    }
}

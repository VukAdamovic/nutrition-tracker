package com.example.myapplication.modules;

import com.example.myapplication.data.datasources.remote.CalorieService;
import com.example.myapplication.data.repositories.remote.calories.CalorieRepository;
import com.example.myapplication.data.repositories.remote.calories.CalorieRepositoryImpl;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

@Module
public class CaloriesModule {

    @Provides
    public CalorieRepository provideCalorieRepository(CalorieService calorieService) {
        return new CalorieRepositoryImpl(calorieService);
    }

    @Provides
    public CalorieService provideCalorieService(@Named("ninja") Retrofit retrofit) {
        return retrofit.create(CalorieService.class);
    }
}

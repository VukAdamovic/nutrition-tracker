package com.example.myapplication.modules;

import com.example.myapplication.data.datasources.remote.CaloriesService;
import com.example.myapplication.data.repositories.remote.calories.CaloriesRepository;
import com.example.myapplication.data.repositories.remote.calories.CaloriesRepositoryImpl;

import javax.inject.Named;

import dagger.Provides;
import retrofit2.Retrofit;

public class CaloriesModule {

    @Provides
    public CaloriesRepository provideCaloriesRepository(CaloriesService caloriesService) {
        return new CaloriesRepositoryImpl(caloriesService);
    }

    @Provides
    public CaloriesService provideCaloriesService(@Named("ninja") Retrofit retrofit) {
        return retrofit.create(CaloriesService.class);
    }
}

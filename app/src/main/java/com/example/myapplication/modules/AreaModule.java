package com.example.myapplication.modules;

import com.example.myapplication.data.datasources.remote.AreaService;
import com.example.myapplication.data.repositories.remote.area.AreaRepository;
import com.example.myapplication.data.repositories.remote.area.AreaRepositoryImpl;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

@Module
public class AreaModule {

    @Provides
    public AreaRepository provideAreaRepository(AreaService areaService) {
        return new AreaRepositoryImpl(areaService);
    }

    @Provides
    public AreaService provideAreaService(@Named("mealdb") Retrofit retrofit) {
        return retrofit.create(AreaService.class);
    }
}

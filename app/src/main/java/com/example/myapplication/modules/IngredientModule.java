package com.example.myapplication.modules;

import com.example.myapplication.data.datasources.remote.IngredientService;
import com.example.myapplication.data.repositories.remote.ingredient.IngredientRepository;
import com.example.myapplication.data.repositories.remote.ingredient.IngredientRepositoryImpl;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

@Module
public class IngredientModule {

    @Provides
    public IngredientRepository provideIngredientRepository(IngredientService ingredientService) {
        return new IngredientRepositoryImpl(ingredientService);
    }

    @Provides
    public IngredientService provideIngredientService(Retrofit retrofit) {
        return retrofit.create(IngredientService.class);
    }
}

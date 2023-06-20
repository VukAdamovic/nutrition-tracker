package com.example.myapplication.modules;

import com.example.myapplication.data.datasources.remote.CategoryService;
import com.example.myapplication.data.repositories.remote.category.CategoryRepository;
import com.example.myapplication.data.repositories.remote.category.CategoryRepositoryImpl;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

@Module
public class CategoryModule {

    @Provides
    public CategoryRepository provideCategoryRepository(CategoryService categoryService) {
        return new CategoryRepositoryImpl(categoryService);
    }

    @Provides
    public CategoryService provideCategoryService(Retrofit retrofit) {
        return retrofit.create(CategoryService.class);
    }
}


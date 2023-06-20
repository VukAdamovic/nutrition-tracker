package com.example.myapplication.data.datasources.remote;

import com.example.myapplication.data.models.api.category.AllCategoriesResponse;

import io.reactivex.Observable;
import retrofit2.http.GET;

public interface CategoryService {

    @GET("categories.php")
    Observable<AllCategoriesResponse> getAllCategories();
}

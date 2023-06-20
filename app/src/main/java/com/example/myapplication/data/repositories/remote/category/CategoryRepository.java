package com.example.myapplication.data.repositories.remote.category;

import com.example.myapplication.data.models.api.domain.Category;

import java.util.List;

import io.reactivex.Observable;

public interface CategoryRepository {

    Observable<List<Category>> getAllCategories();

}

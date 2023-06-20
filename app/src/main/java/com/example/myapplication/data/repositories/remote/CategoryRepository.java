package com.example.myapplication.data.repositories.remote;

import com.example.myapplication.data.models.domain.Category;

import java.util.List;

import io.reactivex.Observable;

public interface CategoryRepository {

    Observable<List<Category>> getAll();

}

package com.example.myapplication.data.repositories.remote.category;

import com.example.myapplication.data.datasources.remote.CategoryService;
import com.example.myapplication.data.models.api.category.AllCategoriesResponse;
import com.example.myapplication.data.models.api.category.CategoryResponse;
import com.example.myapplication.data.models.api.domain.Category;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.functions.Function;

public class CategoryRepositoryImpl implements CategoryRepository{


    private CategoryService categoryService;

    @Inject
    public CategoryRepositoryImpl(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @Override
    public Observable<List<Category>> getAllCategories() {
        return categoryService
                .getAllCategories()
                .map(new Function<AllCategoriesResponse, List<Category>>() {
                    @Override
                    public List<Category> apply(AllCategoriesResponse allCategoriesResponse) throws Exception {
                        List<Category> categories = new ArrayList<>();
                        if(allCategoriesResponse != null && allCategoriesResponse.getAllCategories() != null){
                            for (CategoryResponse categoryResponse : allCategoriesResponse.getAllCategories()) {
                                categories.add(new Category(
                                        categoryResponse.getIdCategory(),
                                        categoryResponse.getStrCategory(),
                                        categoryResponse.getStrCategoryThumb(),
                                        categoryResponse.getStrCategoryDescription()
                                ));
                            }
                        }
                        return categories;
                    }
                });
    }


}

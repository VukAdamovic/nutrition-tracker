package com.example.myapplication.data.models.api;

import com.squareup.moshi.Json;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AllCategoriesResponse {

    @Json(name = "allCategories")
    private List<CategoryResponse> allCategories;

    public AllCategoriesResponse(List<CategoryResponse> allCategories) {
        this.allCategories = allCategories;
    }
}

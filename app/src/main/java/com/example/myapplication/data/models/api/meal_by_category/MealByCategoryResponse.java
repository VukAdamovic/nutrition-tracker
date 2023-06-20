package com.example.myapplication.data.models.api.meal_by_category;

import com.squareup.moshi.Json;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MealByCategoryResponse {

    @Json(name = "idMeal")
    private String id;
    @Json(name = "strMealThumb")
    private String thumbnail;
    @Json(name = "strMeal")
    private String name;

    public MealByCategoryResponse(String id, String thumbnail, String name) {
        this.id = id;
        this.thumbnail = thumbnail;
        this.name = name;
    }
}

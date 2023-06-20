package com.example.myapplication.data.models.api.meal_by_category;

import com.squareup.moshi.Json;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AllMealsByCategoryResponse {

    @Json(name = "meals")
    private List<MealByCategoryResponse> allMeals;

    public AllMealsByCategoryResponse(List<MealByCategoryResponse> allMeals) {
        this.allMeals = allMeals;
    }
}

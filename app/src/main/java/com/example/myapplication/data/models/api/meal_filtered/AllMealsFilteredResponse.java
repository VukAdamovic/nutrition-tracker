package com.example.myapplication.data.models.api.meal_filtered;

import com.squareup.moshi.Json;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AllMealsFilteredResponse {

    @Json(name = "meals")
    private List<MealFilteredResponse> allMeals;

    public AllMealsFilteredResponse(List<MealFilteredResponse> allMeals) {
        this.allMeals = allMeals;
    }
}

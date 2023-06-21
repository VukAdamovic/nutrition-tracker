package com.example.myapplication.data.models.api.meal;

import com.squareup.moshi.Json;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AllMealsResponse {

    @Json(name = "meals")
    private List<SingleMealResponse> allMeals;

    public AllMealsResponse(List<SingleMealResponse> allMeals) {
        this.allMeals = allMeals;
    }
}

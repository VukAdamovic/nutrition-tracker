package com.example.myapplication.data.models.api.calories;

import com.squareup.moshi.Json;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CalorieResponse {

    @Json(name = "calories")
    private String calories;

    public CalorieResponse(String calories) {
        this.calories = calories;
    }
}

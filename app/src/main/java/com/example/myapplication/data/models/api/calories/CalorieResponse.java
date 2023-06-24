package com.example.myapplication.data.models.api.calories;

import com.squareup.moshi.Json;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CalorieResponse {

    @Json(name = "calories")
    private String calories;

    @Json(name = "name")
    private String name;

    @Json(name ="serving_size_g")
    private String serving_size;

    public CalorieResponse(String calories, String name, String serving_size) {
        this.calories = calories;
        this.name = name;
        this.serving_size = serving_size;
    }
}

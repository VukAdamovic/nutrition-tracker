package com.example.myapplication.data.models.api.domain;


import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MealSingle {

    private int id;

    private String mealName;

    private String mealImageUrl;

    private String instructions;

    private String youTubeLink;

    private List<String> ingredientsMeasurements;

    private String category;

    private String area;

    private List<String> tags;

    private double calories;

    public MealSingle(String id, String mealName, String mealImageUrl, String instructions, String youTubeLink,
                      List<String> ingredientsMeasurements, String category, String area, List<String> tags, double calories) {
        this.id = Integer.parseInt(id);
        this.mealName = mealName;
        this.mealImageUrl = mealImageUrl;
        this.instructions = instructions;
        this.youTubeLink = youTubeLink;
        this.ingredientsMeasurements = ingredientsMeasurements;
        this.category = category;
        this.area = area;
        this.tags = tags;
        this.calories = calories;
    }
}

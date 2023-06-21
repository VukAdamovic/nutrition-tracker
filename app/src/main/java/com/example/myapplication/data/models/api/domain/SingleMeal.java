package com.example.myapplication.data.models.api.domain;


import java.util.List;

public class SingleMeal {

    private int id;

    private String mealName;

    private String mealImageUrl;

    private String instructions;

    private String youTubeLink;

    private List<String> ingredientsMeasurements;

    private String category;

    private double calories;

    public SingleMeal(String id, String mealName, String mealImageUrl, String instructions, String youTubeLink,
                      List<String> ingredientsMeasurements, String category, double calories) {
        this.id = Integer.parseInt(id);
        this.mealName = mealName;
        this.mealImageUrl = mealImageUrl;
        this.instructions = instructions;
        this.youTubeLink = youTubeLink;
        this.ingredientsMeasurements = ingredientsMeasurements;
        this.category = category;
        this.calories = calories;
    }
}

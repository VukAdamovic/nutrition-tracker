package com.example.myapplication.data.models.api.domain;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PlanMeal {

    private Integer id;

    private String name;

    private String imageUrl;

    private String mealType;

    private String mealDay;

    private double calories;

    public PlanMeal(Integer id, String name, String imageUrl, String mealType, String mealDay, double calories) {
        this.id = id;
        this.name = name;
        this.imageUrl = imageUrl;
        this.mealType = mealType;
        this.mealDay = mealDay;
        this.calories = calories;
    }
}

package com.example.myapplication.data.models.api.calories;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AllCaloriesResponse {

    private List<CalorieResponse> items;

    public AllCaloriesResponse(List<CalorieResponse> items) {
        this.items = items;
    }
}

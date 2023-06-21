package com.example.myapplication.data.models.api.ingredient;

import com.squareup.moshi.Json;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AllIngredientsResponse {

    @Json(name = "meals")
    private List<IngredientResponse> allIngredients;

    public AllIngredientsResponse(List<IngredientResponse> allIngredients) {
        this.allIngredients = allIngredients;
    }
}

package com.example.myapplication.data.models.api.ingredient;

import com.squareup.moshi.Json;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class IngredientResponse {

    @Json(name = "idIngredient")
    private String idIngredient;

    @Json(name = "strIngredient")
    private String strIngredient;

    @Json(name = "strDescription")
    private String strDescription;

    @Json(name = "strType")
    private String strType;

    public IngredientResponse(String idIngredient, String strIngredient, String strDescription, String strType) {
        this.idIngredient = idIngredient;
        this.strIngredient = strIngredient;
        this.strDescription = strDescription;
        this.strType = strType;
    }
}

package com.example.myapplication.data.models.api;

import com.squareup.moshi.Json;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoryResponse {

    @Json(name = "idCategory")
    private String idCategory;
    @Json(name = "strCategory")
    private String strCategory;
    @Json(name = "strCategoryThumb")
    private String strCategoryThumb;
    @Json(name = "strCategoryDescription")
    private String strCategoryDescription;

    public CategoryResponse(String idCategory, String strCategory, String strCategoryThumb, String strCategoryDescription) {
        this.idCategory = idCategory;
        this.strCategory = strCategory;
        this.strCategoryThumb = strCategoryThumb;
        this.strCategoryDescription = strCategoryDescription;
    }

}

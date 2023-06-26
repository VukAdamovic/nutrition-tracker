package com.example.myapplication.data.models.api.area;

import com.squareup.moshi.Json;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AreaResponse {

    @Json(name = "strArea")
    private String area;

    public AreaResponse(String area) {
        this.area = area;
    }
}

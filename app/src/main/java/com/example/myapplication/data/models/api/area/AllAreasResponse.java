package com.example.myapplication.data.models.api.area;

import com.squareup.moshi.Json;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AllAreasResponse {

    @Json(name = "meals")
    private List<AreaResponse> allAreas;

    public AllAreasResponse(List<AreaResponse> allAreas) {
        this.allAreas = allAreas;
    }
}

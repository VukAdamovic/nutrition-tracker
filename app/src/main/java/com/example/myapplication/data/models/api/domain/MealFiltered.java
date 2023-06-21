package com.example.myapplication.data.models.api.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MealFiltered {

    private int id;
    private String thumbnail;
    private String name;

    public MealFiltered(String id, String thumbnail, String name) {
        this.id = Integer.parseInt(id);
        this.thumbnail = thumbnail;
        this.name = name;
    }
}

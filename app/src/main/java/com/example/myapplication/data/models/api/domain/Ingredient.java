package com.example.myapplication.data.models.api.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Ingredient {

    private int id;

    private int name;

    private String description;

    public Ingredient(String id, int name, String description) {
        this.id = Integer.parseInt(id);
        this.name = name;
        this.description = description;
    }
}

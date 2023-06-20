package com.example.myapplication.data.models.api.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Category {

    private int id;
    private String name;
    private String thumbnail;
    private String description;

    public Category(String id, String name, String thumbnail, String description) {
        this.id = Integer.parseInt(id);
        this.name = name;
        this.thumbnail = thumbnail;
        this.description = description;
    }
}

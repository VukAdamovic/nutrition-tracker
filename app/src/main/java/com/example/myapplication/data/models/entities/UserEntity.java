package com.example.myapplication.data.models.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Entity(tableName = "users")
public class UserEntity {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String username;
    private String password;

}

package com.example.myapplication.data.models.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.Index;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity(tableName = "users", indices = {@Index(value = {"username"}, unique = true)})
public class UserEntity {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String username;
    private String password;

    public UserEntity(int id, String username, String password) {
        this.id = id;
        this.username = username;
        this.password = password;
    }
}


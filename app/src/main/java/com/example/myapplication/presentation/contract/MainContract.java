package com.example.myapplication.presentation.contract;

import com.example.myapplication.data.models.entities.UserEntity;

public interface MainContract {
    void getUserById(long id);
    void getUserByUsernameAndPassword(String username, String password);
    void adduser(UserEntity userEntity);
}

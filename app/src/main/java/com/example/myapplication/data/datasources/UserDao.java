package com.example.myapplication.data.datasources;

import androidx.room.Dao;
import androidx.room.Query;
import com.example.myapplication.data.models.entities.UserEntity;

import io.reactivex.Single;

@Dao
public abstract class UserDao {

    @Query("SELECT * FROM users WHERE id = :id")
    public abstract Single<UserEntity> getById(long id);

    @Query("SELECT * FROM users WHERE username = :username AND password = :password")
    public abstract Single<UserEntity> getByUsernameAndPassword(String username, String password);
}

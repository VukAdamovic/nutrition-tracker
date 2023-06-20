package com.example.myapplication.data.datasources.local;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.myapplication.data.models.entities.UserEntity;

import io.reactivex.Completable;
import io.reactivex.Single;

@Dao
public abstract class UserDao {

    @Query("SELECT * FROM users WHERE id = :id")
    public abstract Single<UserEntity> getById(long id);

    @Query("SELECT * FROM users WHERE username = :username AND password = :password")
    public abstract Single<UserEntity> getByUsernameAndPassword(String username, String password);

    @Insert
    public abstract Completable insertUser(UserEntity user);
}

package com.example.myapplication.data.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.myapplication.data.datasources.local.MealDao;
import com.example.myapplication.data.datasources.local.UserDao;
import com.example.myapplication.data.db.converters.DateConverter;
import com.example.myapplication.data.db.converters.StringListConverter;
import com.example.myapplication.data.models.entities.MealEntity;
import com.example.myapplication.data.models.entities.UserEntity;

@Database(
        entities = {UserEntity.class, MealEntity.class},
        version = 2,
        exportSchema = false)
@TypeConverters({StringListConverter.class, DateConverter.class})
public abstract class MyDatabase extends RoomDatabase {
    public abstract UserDao getUserDao();
    public abstract MealDao getMealDao();
}

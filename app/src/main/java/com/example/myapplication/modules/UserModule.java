package com.example.myapplication.modules;

import com.example.myapplication.data.datasources.local.UserDao;
import com.example.myapplication.data.db.MyDatabase;
import com.example.myapplication.data.repositories.UserRepository;
import com.example.myapplication.data.repositories.UserRepositoryImpl;

import dagger.Module;
import dagger.Provides;

@Module
public class UserModule {

    @Provides
    public UserRepository provideUserRepository(UserDao userDao) {
        return new UserRepositoryImpl(userDao);
    }

    @Provides
    public UserDao provideUserDao(MyDatabase myDatabase) {
        return myDatabase.getUserDao();
    }
}

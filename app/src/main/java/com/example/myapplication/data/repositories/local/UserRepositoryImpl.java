package com.example.myapplication.data.repositories.local;

import com.example.myapplication.data.datasources.local.UserDao;
import com.example.myapplication.data.models.entities.UserEntity;
import javax.inject.Inject;
import io.reactivex.Completable;
import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;

public class UserRepositoryImpl implements UserRepository {

    private final UserDao userDao;

    @Inject
    public UserRepositoryImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public Single<UserEntity> getUserById(long id) {
        return userDao.getById(id);
    }

    @Override
    public Single<UserEntity> getUserByUsernameAndPassword(String username, String password) {
        return userDao.getByUsernameAndPassword(username, password);
    }

    @Override
    public Completable insertUser(UserEntity user) {
        return userDao.insertUser(user);
    }

    @Override
    public Completable updateUser(int idUser, String password) {
        return this.userDao.getById(idUser)
                .flatMapCompletable(userEntity -> {
                    userEntity.setPassword(password);
                    return this.userDao.updateUser(userEntity);
                })
                .subscribeOn(Schedulers.io());
    }

}


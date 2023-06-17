package com.example.myapplication.modules;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import androidx.room.Room;

import com.example.myapplication.data.db.MyDatabase;
import com.squareup.moshi.Moshi;
import com.squareup.moshi.adapters.Rfc3339DateJsonAdapter;

import java.util.Date;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class CoreModule {

    @Singleton
    @Provides
    public Context provideContext(Application application) {
        return application;
    }

    @Singleton
    @Provides
    public SharedPreferences provideSharedPreferences(Context context) {
        return context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE);
    }

    @Singleton
    @Provides
    public MyDatabase provideStudentDatabase(Context context) {
        return Room.databaseBuilder(context, MyDatabase.class, "StudentsDb")
                .fallbackToDestructiveMigration()
                .build();
    }

    @Singleton
    @Provides
    public Moshi provideMoshi() {
        return new Moshi.Builder()
                .add(Date.class, new Rfc3339DateJsonAdapter().nullSafe())
                .build();
    }
}


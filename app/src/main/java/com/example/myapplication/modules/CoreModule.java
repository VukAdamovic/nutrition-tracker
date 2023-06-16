package com.example.myapplication.modules;

import android.content.Context;
import android.content.SharedPreferences;
import androidx.room.Room;
import com.squareup.moshi.Moshi;
import com.squareup.moshi.adapters.Rfc3339DateJsonAdapter;
import org.koin.android.ext.koin.AndroidApplication;
import org.koin.android.ext.koin.AndroidContext;
import org.koin.core.module.Module;
import org.koin.dsl.module.ModuleDeclaration;
import org.koin.dsl.module.module;

import java.util.Date;

public class CoreModule {

    public static Module getModule() {
        return module(new ModuleDeclaration() {
            @Override
            public void declare() {
                single(SharedPreferences.class, () -> {
                    AndroidApplication androidApplication = getKoin().get(AndroidApplication.class);
                    Context applicationContext = androidApplication.getApplicationContext();
                    return applicationContext.getSharedPreferences(applicationContext.getPackageName(), Context.MODE_PRIVATE);
                });

                single(StudentDatabase.class, () -> {
                    AndroidContext androidContext = getKoin().get(AndroidContext.class);
                    Context applicationContext = androidContext.getApplicationContext();
                    return Room.databaseBuilder(applicationContext, StudentDatabase.class, "StudentsDb")
                            .fallbackToDestructiveMigration()
                            .build();
                });

                single(Moshi.class, CoreModule::createMoshi);
            }
        });
    }

    private static Moshi createMoshi() {
        return new Moshi.Builder()
                .add(Date.class, new Rfc3339DateJsonAdapter())
                .build();
    }
}

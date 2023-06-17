package com.example.myapplication.application;

import android.app.Application;

public class MyApplication extends Application {

    private AppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();
//        if (BuildConfig.DEBUG) {
//            Timber.plant(new Timber.DebugTree());
//        }
        initDagger();
    }

    private void initDagger() {
        appComponent = DaggerAppComponent.factory().create(this);
        appComponent.inject(this);
    }

    public AppComponent getAppComponent() {
        return appComponent;
    }
}


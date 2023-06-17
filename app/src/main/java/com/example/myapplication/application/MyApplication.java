package com.example.myapplication.application;

import android.app.Application;

import com.example.myapplication.modules.CoreModule;

import timber.log.Timber;

public class MyApplication extends Application {

    private AppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();
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




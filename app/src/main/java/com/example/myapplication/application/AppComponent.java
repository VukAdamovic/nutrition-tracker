package com.example.myapplication.application;

import android.app.Application;

import com.example.myapplication.modules.CoreModule;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;

@Singleton
@Component(modules = {CoreModule.class})
public interface AppComponent {
    void inject(MyApplication app);

    @Component.Factory
    interface Factory {
        AppComponent create(@BindsInstance Application application);
    }
}



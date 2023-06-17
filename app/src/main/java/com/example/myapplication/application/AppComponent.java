package com.example.myapplication.application;

import com.example.myapplication.modules.CoreModule;

import javax.inject.Singleton;
import dagger.Component;

@Singleton
@Component(modules = {CoreModule.class})
public interface AppComponent {
    void inject(MyApplication app);
}


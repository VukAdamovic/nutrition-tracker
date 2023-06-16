package com.example.myapplication.application;

import android.app.Application;

import com.example.myapplication.modules.CoreModule;

import org.koin.android.BuildConfig;
import org.koin.android.ext.koin.AndroidContext;
import org.koin.android.ext.koin.AndroidFileProperties;
import org.koin.android.ext.koin.AndroidLogger;
import org.koin.androidx.fragment.dsl.FragmentFactory;
import org.koin.core.Koin;
import org.koin.core.KoinApplication;
import org.koin.core.context.GlobalContext;
import org.koin.core.context.startKoin;
import org.koin.core.logger.Level;
import org.koin.core.module.Module;

import java.util.Arrays;
import java.util.List;

import timber.log.Timber;

public class Vezbe10Application extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        init();
    }

    private void init() {
        initTimber();
        initKoin();
    }

    private void initTimber() {
        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }
    }

    private void initKoin() {
        List<Module> modules = Arrays.asList(
                CoreModule.getModule(),
        );
        KoinApplication koinApplication = KoinApplication.init();
        koinApplication.printLogger(Level.ERROR);
        koinApplication.androidContext(this);
        koinApplication.fileProperties();
        koinApplication.modules(modules);

        // Ensure Koin is made global
        if (GlobalContext.getOrNull() == null) {
            startKoin(koinApplication);
        }
    }
}

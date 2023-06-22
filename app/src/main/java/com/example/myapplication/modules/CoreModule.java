package com.example.myapplication.modules;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import androidx.room.Room;

import com.example.myapplication.data.db.MyDatabase;
import com.squareup.moshi.Moshi;
import com.squareup.moshi.adapters.Rfc3339DateJsonAdapter;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.moshi.MoshiConverterFactory;

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
    public MyDatabase provideDb(Context context) {
        return Room.databaseBuilder(context, MyDatabase.class, "Db")
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

    @Singleton
    @Provides
    @Named("mealdb")
    public OkHttpClient provideOkHttpClient() {
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.readTimeout(30, TimeUnit.SECONDS);
        httpClient.connectTimeout(30, TimeUnit.SECONDS);
        httpClient.writeTimeout(30, TimeUnit.SECONDS);
        httpClient.addInterceptor(new LoggingInterceptor());

        return httpClient.build();
    }

    @Singleton
    @Provides
    @Named("ninja")
    public OkHttpClient provideOkHttpClientNinja() {
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.readTimeout(30, TimeUnit.SECONDS);
        httpClient.connectTimeout(30, TimeUnit.SECONDS);
        httpClient.writeTimeout(30, TimeUnit.SECONDS);
        httpClient.addInterceptor(new NinjaHeaderInterceptor());  // Add the header interceptor

        return httpClient.build();
    }

    @Singleton
    @Provides
    @Named("mealdb")
    public Retrofit provideRetrofit(Moshi moshi, @Named("mealdb")OkHttpClient httpClient) {
        return new Retrofit.Builder()
                .baseUrl("https://www.themealdb.com/api/json/v1/1/")
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(MoshiConverterFactory.create(moshi).asLenient())
                .client(httpClient)
                .build();
    }

    @Singleton
    @Provides
    @Named("ninja")
    public Retrofit provideRetrofitNinja(Moshi moshi, @Named("ninja") OkHttpClient httpClient) {
        return new Retrofit.Builder()
                .baseUrl("https://api.api-ninjas.com/v1/")
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(MoshiConverterFactory.create(moshi).asLenient())
                .client(httpClient)
                .build();
    }

    public static <T> T create(Class<T> serviceClass, Retrofit retrofit) {
        return retrofit.create(serviceClass);
    }
}

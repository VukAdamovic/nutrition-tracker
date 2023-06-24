package com.example.myapplication.modules;

import androidx.annotation.NonNull;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class LoggingInterceptor implements Interceptor {
    @NonNull
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        HttpUrl url = request.url();

        System.out.println("Request URL: " + url.toString());

        // Proceed with the request and capture the response
        Response response = chain.proceed(request);

        return response;
    }
}




package com.example.myapplication.modules;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class LoggingInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        HttpUrl url = request.url();

        // Log the full URL
        System.out.println("Request URL: " + url.toString());

        // Proceed with the request
        Response response = chain.proceed(request);

        return response;
    }
}


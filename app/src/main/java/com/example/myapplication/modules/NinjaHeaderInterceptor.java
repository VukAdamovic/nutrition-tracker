package com.example.myapplication.modules;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

class NinjaHeaderInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request originalRequest = chain.request();

        Request modifiedRequest = originalRequest.newBuilder()
                .header("X-Api-Key", "tTqErRB9K+QadXhxzMknfg==4yDrM7Nm4vcpTcrJ")
                .build();

        return chain.proceed(modifiedRequest);
    }
}

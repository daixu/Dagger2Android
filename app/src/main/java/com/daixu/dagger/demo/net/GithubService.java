package com.daixu.dagger.demo.net;

import com.daixu.dagger.demo.net.service.ApiServer;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.daixu.dagger.demo.common.Constant.Url.API_SERVER_URL;

public class GithubService {

    private static Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(API_SERVER_URL)
            .client(getOkHttpClient())
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build();

    public static ApiServer createGithubService() {
        return retrofit.create(ApiServer.class);
    }

    private static OkHttpClient getOkHttpClient(){
        Interceptor interceptor = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request()
                        .newBuilder()
                        .addHeader("token", "9b3898a5f207479a87508b46f48cc2af")
                        .build();
                return chain.proceed(request);
            }
        };
        return new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .addInterceptor(new LoggingInterceptor())
                .build();
    }
}
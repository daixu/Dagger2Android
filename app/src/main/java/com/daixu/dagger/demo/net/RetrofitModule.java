package com.daixu.dagger.demo.net;

import javax.inject.Inject;

import io.reactivex.schedulers.Schedulers;
import retrofit2.CallAdapter;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import timber.log.Timber;

import static java.lang.String.format;

/**
 * Created by 32422 on 2017/12/12.
 */

public class RetrofitModule {

    private ApiServer serverApi;

    private Converter.Factory gsonConverterFactory = GsonConverterFactory.create();
    private CallAdapter.Factory rxJavaCallAdapterFactory = RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io());

    @Inject
    RetrofitModule() {
    }

    @Inject
    OkHttpModule mOkHttpModule;

    public ApiServer getServerApi(String baseUrl) {
        Timber.tag("Dagger2").e(format("mOkHttpModule=%s", mOkHttpModule));
        if (serverApi == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .client(mOkHttpModule.providerOkHttpClient())
                    .baseUrl(baseUrl)
                    .addCallAdapterFactory(rxJavaCallAdapterFactory)
                    .addConverterFactory(gsonConverterFactory)
                    .build();

            serverApi = retrofit.create(ApiServer.class);
        }
        return serverApi;
    }
}
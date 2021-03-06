package com.daixu.dagger.demo.net.retrofit;

import android.text.TextUtils;

import com.daixu.dagger.demo.ToDoApplication;
import com.daixu.dagger.demo.utils.LoginStateUtil;
import com.daixu.dagger.demo.utils.RxSPTool;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import io.reactivex.schedulers.Schedulers;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import timber.log.Timber;

import static com.daixu.dagger.demo.common.Constant.Url.API_SERVER_URL;
import static com.daixu.dagger.demo.common.PreferenceKeys.SERVER_URL;
import static java.lang.String.format;

/**
 * Created by 32422 on 2017/11/9.
 */

public class ApiRetrofit {

    private static Retrofit retrofit;

    public ApiRetrofit() {
        String url = RxSPTool.getString(ToDoApplication.getInstance().getApplicationContext(), SERVER_URL);
        if (TextUtils.isEmpty(url)) {
            url = API_SERVER_URL;
        }
        Timber.tag("url=").e(url);
        retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .client(getOkHttpClient())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    private OkHttpClient getOkHttpClient() {
        HttpLoggingInterceptor logInterceptor = new HttpLoggingInterceptor(new HttpLogger());
        logInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        Interceptor interceptor = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                String token = LoginStateUtil.getInstance().getToken();
                Timber.tag("getOkHttpClient").e("token=" + token);
                // String token = ToDoApplication.token;
                Request request = chain.request()
                        .newBuilder()
                        .addHeader("token", token)
                        .build();
                return chain.proceed(request);
            }
        };
        return new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .addInterceptor(logInterceptor)
                .connectTimeout(15, TimeUnit.SECONDS)
                .readTimeout(20, TimeUnit.SECONDS)
                .writeTimeout(20, TimeUnit.SECONDS)
                .build();
    }

    private static class HttpLogger implements HttpLoggingInterceptor.Logger {

        private StringBuilder mMessage = new StringBuilder();

        @Override
        public void log(String message) {
            // 请求或者响应开始
            if (message.startsWith("--> POST")) {
                mMessage.delete(0, mMessage.length());
            }
            mMessage.append(message.concat("\n"));
            // 请求或者响应结束，打印整条日志
            if (message.startsWith("<-- END HTTP")) {
                Timber.tag("Dagger2").e(format("mMessage=%s", mMessage));
                mMessage.delete(0, mMessage.length());
            }
        }
    }

    public Retrofit getRetrofit() {
        return retrofit;
    }
}

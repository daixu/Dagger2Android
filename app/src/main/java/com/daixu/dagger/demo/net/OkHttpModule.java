package com.daixu.dagger.demo.net;

import com.daixu.dagger.demo.utils.LoginStateUtil;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import dagger.Module;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import timber.log.Timber;

import static java.lang.String.format;

/**
 * Created by 32422 on 2017/12/12.
 */
@Module
public class OkHttpModule {

    @Inject
    OkHttpModule() {
    }

    public OkHttpClient providerOkHttpClient() {
        HttpLoggingInterceptor logInterceptor = new HttpLoggingInterceptor(new HttpLogger());
        logInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        Interceptor interceptor = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request()
                        .newBuilder()
                        .addHeader("token", LoginStateUtil.getInstance().getToken())
                        .build();
                return chain.proceed(request);
            }
        };
        return new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .addInterceptor(logInterceptor)
//                    .cookieJar(new PersistentCookieJar(new SetCookieCache(), new SharedPrefsCookiePersistor(App.getContext())))
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
}

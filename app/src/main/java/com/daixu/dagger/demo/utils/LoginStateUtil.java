package com.daixu.dagger.demo.utils;

import android.content.Context;

import javax.inject.Inject;

public class LoginStateUtil {

    @Inject
    LoginStateUtil() {
    }

    private volatile static LoginStateUtil instance;

    public static LoginStateUtil getInstance() {
        if (instance == null) {
            synchronized (LoginStateUtil.class) {
                if (instance == null) {
                    instance = new LoginStateUtil();
                }
            }
        }
        return instance;
    }

    public boolean checkLoginAndRequestLogin(Context context) {
        return true;
    }

    public String getToken() {
//        Log.e("TAG", "mContext=" + mContext);
        return "7dc536a68ea340248353e02a576cd037";
    }
}
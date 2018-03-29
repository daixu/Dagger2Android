package com.daixu.dagger.demo.utils;

import android.content.Context;
import android.text.TextUtils;

import com.daixu.dagger.demo.ToDoApplication;

import javax.inject.Inject;

import static com.daixu.dagger.demo.common.PreferenceKeys.TOKEN;

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
        String token = RxSPTool.getContent(context, TOKEN);
        return !TextUtils.isEmpty(token);
    }

    public String getToken() {
        String token = RxSPTool.getContent(ToDoApplication.getInstance().getApplicationContext(), TOKEN);
        return token;
    }
}
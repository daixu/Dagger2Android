package com.daixu.dagger.demo.data;

import android.text.TextUtils;

import javax.inject.Inject;

/**
 * Created by 32422 on 2017/12/11.
 */

public class TasksRepository implements TasksDataSource {

    @Inject
    TasksRepository() {
    }

    @Override
    public void getUserInfo(String userId, TasksDataSource.GetTaskCallback callback) {
        String userInfo;
        if (!TextUtils.isEmpty(userId)) {
            userInfo = "userId=" + userId + " password=123456";
        } else {
            userInfo = "哈哈哈";
        }
        callback.onTaskLoaded(userInfo);
    }
}

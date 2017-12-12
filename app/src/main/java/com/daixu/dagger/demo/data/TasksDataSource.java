package com.daixu.dagger.demo.data;

/**
 * Created by 32422 on 2017/12/11.
 */

public interface TasksDataSource {

    void getUserInfo(String userInfo, GetTaskCallback callback);

    interface GetTaskCallback {
        void onTaskLoaded(String userInfo);
    }
}

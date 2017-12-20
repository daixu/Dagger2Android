package com.daixu.dagger.demo.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.daixu.dagger.demo.ToDoApplication;

/**
 * Created by 32422 on 2017/10/11.
 */

public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ToDoApplication.getInstance().addActivity(this);
    }

    @Override
    protected void onDestroy() {
        // 将本对象从ActivityStack中移除
        ToDoApplication.getInstance().removeActivity(this);
        super.onDestroy();
    }
}

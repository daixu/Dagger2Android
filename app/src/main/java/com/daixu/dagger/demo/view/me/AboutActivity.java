package com.daixu.dagger.demo.view.me;

import android.content.pm.ShortcutManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import com.daixu.dagger.demo.R;
import com.daixu.dagger.demo.ToDoApplication;
import com.daixu.dagger.demo.view.BaseActivity;

import java.util.ArrayList;
import java.util.List;

public class AboutActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        findViewById(R.id.btn_exit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                exit();
            }
        });
    }

    void exit() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N_MR1) {
            return;
        }

        ShortcutManager shortcutManager = getSystemService(ShortcutManager.class);

        List<String> list = new ArrayList<>();
        list.add("my_wallet");
        list.add("my_order");

        if (null != shortcutManager) {
            shortcutManager.removeDynamicShortcuts(list);
        }
        ToDoApplication.getInstance().finishAllActivity();
        finish();
    }
}

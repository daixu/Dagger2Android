package com.daixu.dagger.demo.view.me;

import android.content.Intent;
import android.content.pm.ShortcutManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import com.daixu.dagger.demo.R;
import com.daixu.dagger.demo.ToDoApplication;
import com.daixu.dagger.demo.view.BaseActivity;
import com.daixu.dagger.demo.view.dev.DeveloperActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class AboutActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        ButterKnife.bind(this);
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

    @OnClick({R.id.btn_close, R.id.btn_exit, R.id.btn_dev})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_close: {
                finish();
            }
            break;
            case R.id.btn_exit: {
                exit();
            }
            break;
            case R.id.btn_dev: {
                Intent intent = new Intent(this, DeveloperActivity.class);
                startActivity(intent);
            }
            break;
            default:
                break;
        }
    }
}

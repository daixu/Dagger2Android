package com.daixu.dagger.demo.view.me;

import android.content.pm.ShortcutManager;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.Toast;

import com.daixu.dagger.demo.R;
import com.daixu.dagger.demo.ToDoApplication;
import com.daixu.dagger.demo.utils.RxSPTool;
import com.daixu.dagger.demo.view.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.daixu.dagger.demo.common.PreferenceKeys.IS_DEV;
import static com.daixu.dagger.demo.common.PreferenceKeys.TOKEN;

public class AboutActivity extends BaseActivity {
    private long[] mHits = new long[7];

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

        RxSPTool.clearPreference(this, IS_DEV);
        RxSPTool.clearPreference(this, TOKEN);
        ToDoApplication.getInstance().finishAllActivity();
        finish();
    }

    @OnClick({R.id.btn_close, R.id.btn_exit, R.id.tv_about})
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
            case R.id.tv_about: {
                boolean isDev = RxSPTool.getBoolean(this, IS_DEV);
                if (isDev) {
                    Toast.makeText(this, "已处于开发者模式", Toast.LENGTH_SHORT).show();
                } else {
                    System.arraycopy(mHits, 1, mHits, 0, mHits.length - 1);
                    mHits[mHits.length - 1] = SystemClock.uptimeMillis();
                    if (mHits[0] >= (SystemClock.uptimeMillis() - 1000)) {
                        RxSPTool.putBoolean(this, IS_DEV, true);
                        Toast.makeText(this, "已启用开发者模式", Toast.LENGTH_SHORT).show();
                    }
                }
            }
            default:
                break;
        }
    }
}

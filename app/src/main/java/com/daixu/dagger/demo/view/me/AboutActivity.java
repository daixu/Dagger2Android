package com.daixu.dagger.demo.view.me;

import android.content.Intent;
import android.content.pm.ShortcutManager;
import android.os.Build;
import android.os.Bundle;
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
    private int mDevHitCountdown;
    static final int TAPS_TO_BE_A_DEVELOPER = 7;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        ButterKnife.bind(this);

        showTitleLine();
        showBackImg();

        setTitleText(R.string.tv_about);

        mDevHitCountdown = TAPS_TO_BE_A_DEVELOPER;
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

    @OnClick({R.id.btn_close, R.id.btn_exit, R.id.tv_about, R.id.btn_load_patch})
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
                    Toast.makeText(this, getString(R.string.show_dev_already), Toast.LENGTH_SHORT).show();
                } else {
                    if (mDevHitCountdown > 0) {
                        mDevHitCountdown--;
                        if (mDevHitCountdown == 0) {
                            mDevHitCountdown++;
                            Intent intent = new Intent(this, VerificationActivity.class);
                            startActivity(intent);
                        } else if (mDevHitCountdown > 0 && mDevHitCountdown < (TAPS_TO_BE_A_DEVELOPER - 2)) {
                            Toast.makeText(this, getResources().getQuantityString(R.plurals.show_dev_countdown, mDevHitCountdown, mDevHitCountdown), Toast.LENGTH_SHORT).show();
                        }
                    } else if (mDevHitCountdown < 0) {
                        Toast.makeText(this, getString(R.string.show_dev_already), Toast.LENGTH_SHORT).show();
                    }
                }
            }
            break;
            case R.id.btn_load_patch: {
            }
            break;
            default:
                break;
        }
    }
}

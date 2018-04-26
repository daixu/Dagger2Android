package com.daixu.dagger.demo.view.splash;

import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.text.TextUtils;

import com.daixu.dagger.demo.R;
import com.daixu.dagger.demo.utils.RxHelper;
import com.daixu.dagger.demo.utils.RxSPTool;
import com.daixu.dagger.demo.view.BaseActivity;
import com.daixu.dagger.demo.view.login.LoginActivity;
import com.daixu.dagger.demo.view.main.MainActivity;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.daixu.dagger.demo.common.PreferenceKeys.TOKEN;

public class SplashActivity extends BaseActivity {

    @BindView(R.id.layout_splash)
    ConstraintLayout mLayoutSplash;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);

        updateViews();
    }

//    @Override
//    public void onWindowFocusChanged(boolean hasFocus) {
//        super.onWindowFocusChanged(hasFocus);
//        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
//                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY | View.SYSTEM_UI_FLAG_FULLSCREEN);
//    }

    protected void updateViews() {
        RxHelper.countdown(3)
                .compose(this.<Integer>bindToLifecycle())
                .subscribe(new Subscriber<Integer>() {

                    @Override
                    public void onSubscribe(Subscription s) {
                        s.request(Long.MAX_VALUE);
                    }

                    @Override
                    public void onNext(Integer integer) {
                        // mSbSkip.setText("跳过 " + integer);
                    }

                    @Override
                    public void onError(Throwable e) {
                        onDoSkip();
                    }

                    @Override
                    public void onComplete() {
                        onDoSkip();
                    }
                });
    }

    private void onDoSkip() {
        String token = RxSPTool.getString(this, TOKEN);
        if (!TextUtils.isEmpty(token)) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
            // ARouter.getInstance().build("/jy/main").navigation();
        } else {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            finish();
//            ARouter.getInstance().build("/jy/login").navigation();
        }
    }

    @Override
    public void onBackPressed() {
        // 不响应后退键
        return;
    }

}
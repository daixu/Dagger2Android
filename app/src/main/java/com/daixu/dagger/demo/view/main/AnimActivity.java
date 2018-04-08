package com.daixu.dagger.demo.view.main;

import android.content.Intent;
import android.os.Bundle;

import com.daixu.dagger.demo.R;
import com.daixu.dagger.demo.utils.RxHelper;
import com.daixu.dagger.demo.view.BaseActivity;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import butterknife.ButterKnife;

import static com.daixu.dagger.demo.common.Constant.FinishActivity.RECEIVER_ACTION_FINISH;

public class AnimActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anim);
        ButterKnife.bind(this);

        updateViews();
    }

    protected void updateViews() {
        RxHelper.countdown(5)
                .compose(this.<Integer>bindToLifecycle())
                .subscribe(new Subscriber<Integer>() {

                    @Override
                    public void onSubscribe(Subscription s) {
                        s.request(Long.MAX_VALUE);
                    }

                    @Override
                    public void onNext(Integer integer) {
                    }

                    @Override
                    public void onError(Throwable e) {
                        Intent intent = new Intent(RECEIVER_ACTION_FINISH);
                        sendBroadcast(intent);
                        Intent anim = new Intent(AnimActivity.this, Main2Activity.class);
                        startActivity(anim);
                        finish();
                    }

                    @Override
                    public void onComplete() {
                        Intent intent = new Intent(RECEIVER_ACTION_FINISH);
                        sendBroadcast(intent);
                        Intent anim = new Intent(AnimActivity.this, Main2Activity.class);
                        startActivity(anim);
                        finish();
                    }
                });
    }
}

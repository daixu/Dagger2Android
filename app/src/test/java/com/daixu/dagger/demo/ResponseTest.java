package com.daixu.dagger.demo;

import com.daixu.dagger.demo.bean.OnlyUserIdReq;
import com.daixu.dagger.demo.bean.UserReceivingAreaResp;
import com.daixu.dagger.demo.net.GithubService;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowLog;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 23)
public class ResponseTest {

    @Rule
    public RxSchedulersOverrideRule rule = new RxSchedulersOverrideRule();

    @Before
    public void setUp() {
        ShadowLog.stream = System.out;
    }

    @Test
    public void selUserReceivingAreaTest() {
        OnlyUserIdReq req = new OnlyUserIdReq();
        req.userId = "738910b1-a828-46cf-bcbb-8ccd28a23b7b";
        GithubService.createGithubService()
                .selUserReceivingArea(req)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<UserReceivingAreaResp>() {
                    @Override
                    public void onSubscribe(Subscription s) {

                    }

                    @Override
                    public void onNext(UserReceivingAreaResp userReceivingAreaResp) {
                        Timber.tag("selUserReceivingAreaTest").e(String.format("userReceivingAreaResp=%s", userReceivingAreaResp.data));
                    }

                    @Override
                    public void onError(Throwable t) {

                    }

                    @Override
                    public void onComplete() {
                    }
                });
    }
}
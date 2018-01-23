package com.daixu.dagger.demo;

import com.daixu.dagger.demo.bean.GetGoodsTotalReq;
import com.daixu.dagger.demo.bean.GetGoodsTotalResp;
import com.daixu.dagger.demo.bean.OnlyUserIdReq;
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
public class ResponseTotalTest {

    @Rule
    public RxSchedulersOverrideRule rule = new RxSchedulersOverrideRule();

    @Before
    public void setUp() {
        ShadowLog.stream = System.out;
    }

    @Test
    public void selUserReceivingAreaTest() {
        GetGoodsTotalReq req = new GetGoodsTotalReq();
        req.userId = "738910b1-a828-46cf-bcbb-8ccd28a23b7b";
        req.operateType = "3";
        GithubService.createGithubService()
                .userShoppingTrolleyGoodsTotal(req)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<String>() {
                    @Override
                    public void onSubscribe(Subscription s) {

                    }

                    @Override
                    public void onNext(String resp) {
                        Timber.tag("userShoppingTrolleyGoodsTotal").e(resp);
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
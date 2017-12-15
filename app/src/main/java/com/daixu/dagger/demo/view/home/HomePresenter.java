package com.daixu.dagger.demo.view.home;

import com.daixu.dagger.demo.bean.BannerResp;
import com.daixu.dagger.demo.net.BaseSubscriber;
import com.daixu.dagger.demo.net.ExceptionHandle;
import com.daixu.dagger.demo.net.service.ApiServer;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by 32422 on 2017/12/9.
 */

public class HomePresenter implements HomeContract.Presenter {
    private HomeContract.View mView;
    private ApiServer mApiServer;

    @Inject
    HomePresenter(ApiServer apiServer) {
        mApiServer = apiServer;
    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unSubscribe() {
        mView = null;
    }

    @Override
    public void takeView(HomeContract.View view) {
        mView = view;
    }

    @Override
    public void loadHomeBanner() {
        mApiServer.areaSlideShows()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<BannerResp>() {
                    @Override
                    protected void hideDialog() {

                    }

                    @Override
                    protected void showDialog() {

                    }

                    @Override
                    public void onError(ExceptionHandle.ResponseThrowable e) {
                        mView.updateFailure();
                    }

                    @Override
                    public void onNext(BannerResp resp) {
                        if (null != resp) {
                            if (resp.isOk()) {
                                mView.updateBanner(resp);
                            } else {
                                mView.updateFailure();
                            }
                        } else {
                            mView.updateFailure();
                        }
                    }
                });
    }
}

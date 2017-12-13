package com.daixu.dagger.demo.view.shopcart;

import com.daixu.dagger.demo.bean.GetShopCartResp;
import com.daixu.dagger.demo.bean.OnlyUserIdReq;
import com.daixu.dagger.demo.net.ApiServer;
import com.daixu.dagger.demo.net.BaseSubscriber;
import com.daixu.dagger.demo.net.ExceptionHandle;
import com.daixu.dagger.demo.net.RetrofitModule;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

import static com.daixu.dagger.demo.net.ApiServer.API_SERVER_URL;

/**
 * Created by 32422 on 2017/12/9.
 */

public class ShoppingCartPresenter implements ShoppingCartContract.Presenter {
    private ShoppingCartContract.View mView;
    private ApiServer mApiServer;

    @Inject
    ShoppingCartPresenter(RetrofitModule retrofitModule) {
        mApiServer = retrofitModule.getServerApi(API_SERVER_URL);
    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unSubscribe() {
        mView = null;
    }

    @Override
    public void takeView(ShoppingCartContract.View view) {
        mView = view;
    }

    @Override
    public void loadShopCartList(String userId) {
        OnlyUserIdReq req = new OnlyUserIdReq();
        req.userId = userId;
        mApiServer.userShoppingTrolleyList(req)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<GetShopCartResp>() {
                    @Override
                    protected void hideDialog() {

                    }

                    @Override
                    protected void showDialog() {

                    }

                    @Override
                    public void onError(ExceptionHandle.ResponseThrowable e) {
                        mView.loadShopCartFailure();
                    }

                    @Override
                    public void onNext(GetShopCartResp resp) {
                        if (null != resp) {
                            if (resp.isOk()) {
                                mView.loadShopCartSuccess(resp);
                            } else {
                                mView.loadShopCartFailure();
                            }
                        } else {
                            mView.loadShopCartFailure();
                        }
                    }
                });
    }
}

package com.daixu.dagger.demo.view.order;

import com.daixu.dagger.demo.bean.GetUserOrdersReq;
import com.daixu.dagger.demo.bean.GetUserOrdersResp;
import com.daixu.dagger.demo.net.BaseSubscriber;
import com.daixu.dagger.demo.net.ExceptionHandle;
import com.daixu.dagger.demo.net.service.ApiServer;
import com.daixu.dagger.demo.utils.RxUtil;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

public class MyOrderPresenter implements MyOrderContract.Presenter {
    private MyOrderContract.View mView;
    private ApiServer mApiServer;

    @Inject
    MyOrderPresenter(ApiServer apiServer) {
        mApiServer = apiServer;
    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unSubscribe() {
        Timber.tag("MyOrderPresenter").e("unSubscribe");
        mView = null;
    }

    @Override
    public void takeView(MyOrderContract.View view) {
        mView = view;
    }

    @Override
    public void getUserOrders(String userId, int pageNum, int orderStatus) {
        GetUserOrdersReq req = new GetUserOrdersReq();
        req.userId = userId;
        req.pageNum = pageNum;
        req.pageSize = 20;
        req.orderStatus = orderStatus;
        mApiServer.getUserOrders(req)
                .compose(RxUtil.<GetUserOrdersResp>applySchedulers(RxUtil.IO_ON_UI_TRANSFORMER_BACK_PRESSURE))
                .compose(mView.<GetUserOrdersResp>bindToLife())
                .subscribe(new BaseSubscriber<GetUserOrdersResp>() {
                    @Override
                    protected void hideDialog() {

                    }

                    @Override
                    protected void showDialog() {

                    }

                    @Override
                    public void onError(ExceptionHandle.ResponseThrowable e) {
                        mView.getUserOrdersFailure();
                    }

                    @Override
                    public void onNext(GetUserOrdersResp resp) {
                        if (null != resp) {
                            mView.updateUserOrders(resp);
                        } else {
                            mView.getUserOrdersFailure();
                        }
                    }
                });
    }
}

package com.daixu.dagger.demo.view.todo;

import com.daixu.dagger.demo.bean.GetUserTodoReq;
import com.daixu.dagger.demo.bean.GetUserTodoResp;
import com.daixu.dagger.demo.net.BaseSubscriber;
import com.daixu.dagger.demo.net.ExceptionHandle;
import com.daixu.dagger.demo.net.service.ApiServer;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by 32422 on 2017/12/9.
 */

public class TodoPresenter implements TodoContract.Presenter {
    private TodoContract.View mView;
    private ApiServer mApiServer;

    @Inject
    TodoPresenter(ApiServer apiServer) {
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
    public void takeView(TodoContract.View view) {
        mView = view;
    }

    @Override
    public void loadUserTodoList(String userId, String categoryId) {
        GetUserTodoReq req = new GetUserTodoReq();
        req.userId = userId;
        req.categoryId = categoryId;
        mApiServer.commonUserGreensList(req)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<GetUserTodoResp>() {
                    @Override
                    protected void hideDialog() {

                    }

                    @Override
                    protected void showDialog() {

                    }

                    @Override
                    public void onError(ExceptionHandle.ResponseThrowable e) {
                        mView.loadUserTodoFailure();
                    }

                    @Override
                    public void onNext(GetUserTodoResp resp) {
                        if (null != resp) {
                            if (resp.isOk()) {
                                mView.loadUserTodoSuccess(resp);
                            } else {
                                mView.loadUserTodoFailure();
                            }
                        } else {
                            mView.loadUserTodoFailure();
                        }
                    }
                });
    }
}

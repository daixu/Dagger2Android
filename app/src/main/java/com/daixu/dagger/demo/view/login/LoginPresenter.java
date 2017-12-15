package com.daixu.dagger.demo.view.login;

import com.daixu.dagger.demo.bean.LoginReq;
import com.daixu.dagger.demo.bean.LoginResp;
import com.daixu.dagger.demo.di.ActivityScoped;
import com.daixu.dagger.demo.net.BaseSubscriber;
import com.daixu.dagger.demo.net.ExceptionHandle;
import com.daixu.dagger.demo.net.service.ApiServer;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

/**
 * Created by 32422 on 2017/12/11.
 */
@ActivityScoped
public class LoginPresenter implements LoginContract.Presenter {
    private LoginContract.View mView;
    private ApiServer mApiServer;

    @Inject
    LoginPresenter(ApiServer apiServer) {
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
    public void takeView(LoginContract.View view) {
        mView = view;
    }

    @Override
    public void login(String phone, String password, String deviceId) {
        if (phone == null || phone.length() == 0) {
            return;
        }
        if (password == null || password.length() == 0) {
            return;
        }
        if (deviceId == null || deviceId.length() == 0) {
            return;
        }

        Timber.tag("Dagger2").e(String.format("mApiServer=%s", mApiServer));
        LoginReq req = new LoginReq();
        req.userName = phone;
        req.password = password;
        req.smsCode = "";
        req.deviceId = deviceId;
        req.terminalType = "3";
        mApiServer.login(req)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<LoginResp>() {
                    @Override
                    protected void hideDialog() {
                    }

                    @Override
                    protected void showDialog() {
                    }

                    @Override
                    public void onError(ExceptionHandle.ResponseThrowable e) {
                        mView.loginFailure(e.message);
                    }

                    @Override
                    public void onNext(LoginResp resp) {
                        if (null != resp) {
                            if (resp.isOk()) {
                                mView.loginSuccess(resp);
                            } else {
                                mView.loginFailure(resp.msg);
                            }
                        } else {
                            mView.loginFailure("");
                        }
                    }
                });
    }
}

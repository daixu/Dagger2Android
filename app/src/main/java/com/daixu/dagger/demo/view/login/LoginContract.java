package com.daixu.dagger.demo.view.login;

import com.daixu.dagger.demo.bean.LoginResp;
import com.daixu.dagger.demo.view.BasePresenter;
import com.daixu.dagger.demo.view.BaseView;

/**
 * Created by 32422 on 2017/12/9.
 */

public interface LoginContract {

    interface Presenter extends BasePresenter {
        void takeView(LoginContract.View view);

        void login(String phone, String password, String deviceId);
    }

    interface View extends BaseView<Presenter> {
        void loginSuccess(LoginResp resp);

        void loginFailure(String msg);
    }
}

package com.daixu.dagger.demo.view.login;

import com.daixu.dagger.demo.RxSchedulersOverrideRule;
import com.daixu.dagger.demo.net.retrofit.ApiRetrofit;
import com.daixu.dagger.demo.net.service.ApiServer;

import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import okhttp3.OkHttpClient;

import static org.mockito.Mockito.verify;

/**
 * Created by 32422 on 2017/12/13.
 */
public class LoginPresenterTest {

    @Mock
    LoginPresenter mPresenter;

    @Mock
    ApiServer mApiServer;

    @Mock
    LoginContract.View mView;

    @Mock
    OkHttpClient mOkHttpClient;

    @ClassRule
    public static RxSchedulersOverrideRule sSchedulersOverrideRule = new RxSchedulersOverrideRule();

    @Before
    public void setupLoginPresenter() {
        MockitoAnnotations.initMocks(this);

        ApiRetrofit retrofit = new ApiRetrofit(mOkHttpClient);
        mApiServer = retrofit.getRetrofit().create(ApiServer.class);
        mPresenter = new LoginPresenter(mApiServer);

        mPresenter.takeView(mView);
    }

    @Test
    public void loginSuccess() throws Exception {
        mPresenter.login("18682367801", "e10adc3949ba59abbe56e057f20f883e", "289bf618-8874-4e1c-8b72-7aceb29fa9e2");

        verify(mView).showProgress();
        verify(mView).dismissProgress();
    }

    @Test
    public void loginFailure() throws Exception {
        mPresenter.login("13800138003", "96e79218965eb72c92a549dd5a330112", "289bf618-8874-4e1c-8b72-7aceb29fa9e2");

        verify(mView).showProgress();
        verify(mView).dismissProgress();

        verify(mView).loginFailure(null);
    }

}
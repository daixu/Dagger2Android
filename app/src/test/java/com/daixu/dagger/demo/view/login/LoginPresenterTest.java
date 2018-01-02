package com.daixu.dagger.demo.view.login;

import com.daixu.dagger.demo.BuildConfig;
import com.daixu.dagger.demo.RxSchedulersOverrideRule;
import com.daixu.dagger.demo.net.retrofit.ApiRetrofit;
import com.daixu.dagger.demo.net.service.ApiServer;
import com.daixu.dagger.demo.utils.MD5;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowLog;

import static org.mockito.Mockito.verify;

/**
 * Created by 32422 on 2017/12/13.
 */
@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 23)
public class LoginPresenterTest {

    @Mock
    LoginPresenter mPresenter;

    @Mock
    ApiServer mApiServer;

    @Mock
    LoginContract.View mView;

    @Rule
    public RxSchedulersOverrideRule rule = new RxSchedulersOverrideRule();

    @Before
    public void setUp() {
        ShadowLog.stream = System.out;

        MockitoAnnotations.initMocks(this);
        ApiRetrofit retrofit = new ApiRetrofit();
        mApiServer = retrofit.getRetrofit().create(ApiServer.class);
        mPresenter = new LoginPresenter(mApiServer);

        mPresenter.takeView(mView);
    }

    @Test
    public void loginSuccess() {
        mPresenter.login("18682367801", MD5.encrypt("123456"), "289bf618-8874-4e1c-8b72-7aceb29fa9e2");
        verify(mView).showProgress();
        verify(mView).dismissProgress();
    }

    @Test
    public void loginFailure() throws Exception {
        mPresenter.login("13800138003", "96e79218965eb72c92a549dd5a330112", "289bf618-8874-4e1c-8b72-7aceb29fa9e2");

        verify(mView).showProgress();
        verify(mView).dismissProgress();

        verify(mView).loginFailure();
    }

}
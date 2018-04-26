package com.daixu.dagger.demo.view.login;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.Toast;

import com.daixu.dagger.demo.R;
import com.daixu.dagger.demo.ToDoApplication;
import com.daixu.dagger.demo.bean.LoginResp;
import com.daixu.dagger.demo.utils.MD5;
import com.daixu.dagger.demo.utils.RxSPTool;
import com.daixu.dagger.demo.view.BaseActivity;
import com.daixu.dagger.demo.view.main.MainActivity;
import com.trello.rxlifecycle2.LifecycleTransformer;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.AndroidInjection;
import timber.log.Timber;

import static com.daixu.dagger.demo.common.PreferenceKeys.TOKEN;
import static com.daixu.dagger.demo.common.PreferenceKeys.USER_ID;

public class LoginActivity extends BaseActivity implements LoginContract.View {

    @Inject
    LoginContract.Presenter mPresenter;
    @BindView(R.id.account)
    EditText mAccount;
    @BindView(R.id.password)
    EditText mPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        findViewById(R.id.email_sign_in_button).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.takeView(this);
    }

    private void attemptLogin() {
        String phone = mAccount.getText().toString();
        String password = mPassword.getText().toString();
        if (TextUtils.isEmpty(phone) && TextUtils.isEmpty(password)) {
            mPresenter.login("13800138004", MD5.encrypt("111111"), "289bf618-8874-4e1c-8b72-7aceb29fa9e2");
        } else {
            mPresenter.login(phone, MD5.encrypt(password), "289bf618-8874-4e1c-8b72-7aceb29fa9e2");
        }
    }

    @Override
    public boolean isActive() {
        return false;
    }

    @Override
    public <T> LifecycleTransformer<T> bindToLife() {
        return this.<T>bindToLifecycle();
    }

    @Override
    public void loginSuccess(LoginResp resp) {
        if (null != resp.data) {
            RxSPTool.putString(this, USER_ID, resp.data.userId);
            RxSPTool.putString(this, TOKEN, resp.data.token);

            ToDoApplication.token = resp.data.token;

            String merchantName = resp.data.merchantName;
            Toast.makeText(this, "resp=" + resp.data.merchantName, Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            intent.putExtra("merchantName", merchantName);
            startActivity(intent);
            finish();
        } else {
            Timber.tag("Dagger2").d("resp.data=null");
        }
    }

    @Override
    public void loginFailure() {
        Timber.tag("Dagger2").d("failure");
    }

    @Override
    public void showProgress() {

    }

    @Override
    public void dismissProgress() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (null != mPresenter) {
            mPresenter.unSubscribe();
        }
    }
}


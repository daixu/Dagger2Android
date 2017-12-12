package com.daixu.dagger.demo.view.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;

import com.daixu.dagger.demo.R;
import com.daixu.dagger.demo.bean.LoginResp;
import com.daixu.dagger.demo.data.TasksDataSource;
import com.daixu.dagger.demo.data.TasksRepository;
import com.daixu.dagger.demo.utils.RxSPTool;
import com.daixu.dagger.demo.view.main.MainActivity;

import javax.inject.Inject;

import dagger.android.AndroidInjection;

import static com.daixu.dagger.demo.common.PreferenceKeys.TOKEN;
import static com.daixu.dagger.demo.common.PreferenceKeys.USER_ID;

public class LoginActivity extends AppCompatActivity implements LoginContract.View {

    @Inject
    LoginContract.Presenter mPresenter;
    @Inject
    TasksRepository mTasksRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        if (null != mTasksRepository) {
            mTasksRepository.getUserInfo("aaa", new TasksDataSource.GetTaskCallback() {
                @Override
                public void onTaskLoaded(String userInfo) {
                    Log.e("LoginActivity", "userInfo=" + userInfo);
                }
            });
        }
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
        mPresenter.login("13800138003", "96e79218965eb72c92a549dd5a330112", "59fb20fb-00b3-4e7b-86db-ea440a814981");
    }

    @Override
    public boolean isActive() {
        return false;
    }

    @Override
    public void loginSuccess(LoginResp resp) {
        if (null != resp.data) {
            RxSPTool.putString(this, USER_ID, resp.data.userId);
            RxSPTool.putString(this, TOKEN, resp.data.token);

            String merchantName = resp.data.merchantName;
            Toast.makeText(this, "resp=" + resp.data.merchantName, Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            intent.putExtra("merchantName", merchantName);
            startActivity(intent);
        } else {
            Log.e("TAG", "resp.data=null");
        }
    }

    @Override
    public void loginFailure(String msg) {
        Log.e("TAG", "msg=" + msg);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (null != mPresenter) {
            mPresenter.unSubscribe();
        }
    }
}


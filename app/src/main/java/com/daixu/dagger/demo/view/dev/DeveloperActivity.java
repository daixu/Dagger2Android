package com.daixu.dagger.demo.view.dev;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.daixu.dagger.demo.R;
import com.daixu.dagger.demo.common.Constant;
import com.daixu.dagger.demo.utils.RxSPTool;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.daixu.dagger.demo.common.PreferenceKeys.TOKEN;
import static com.daixu.dagger.demo.common.PreferenceKeys.USER_ID;

public class DeveloperActivity extends AppCompatActivity {

    @BindView(R.id.tv_server_address)
    TextView mTvServerAddress;
    @BindView(R.id.tv_token)
    TextView mTvToken;
    @BindView(R.id.tv_user_id)
    TextView mTvUserId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_developer);
        ButterKnife.bind(this);

        mTvServerAddress.setText(Constant.Url.API_SERVER_URL);

        String token = RxSPTool.getString(this, TOKEN);
        mTvToken.setText(token);

        String userId = RxSPTool.getString(this, USER_ID);
        mTvUserId.setText(userId);
    }
}

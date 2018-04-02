package com.daixu.dagger.demo.view.dev

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import butterknife.ButterKnife
import com.daixu.dagger.demo.R
import com.daixu.dagger.demo.common.Constant
import com.daixu.dagger.demo.common.PreferenceKeys.TOKEN
import com.daixu.dagger.demo.common.PreferenceKeys.USER_ID
import com.daixu.dagger.demo.utils.RxSPTool
import kotlinx.android.synthetic.main.activity_developer.*

class DeveloperActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_developer)
        ButterKnife.bind(this)


        tv_server_address.text = Constant.Url.API_SERVER_URL;

        val token = RxSPTool.getString(this, TOKEN)
        tv_token.text = token

        val userId = RxSPTool.getString(this, USER_ID)
        tv_user_id.text = userId
    }
}

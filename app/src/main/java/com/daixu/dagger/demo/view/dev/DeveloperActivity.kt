package com.daixu.dagger.demo.view.dev

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import butterknife.ButterKnife
import com.daixu.dagger.demo.R
import com.daixu.dagger.demo.common.Constant
import com.daixu.dagger.demo.common.PreferenceKeys.*
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

        val isAdminDev = RxSPTool.getBoolean(this, IS__ADMIN_DEV)
        if (isAdminDev) {
            btn_edit.visibility = View.VISIBLE
        } else {
            btn_edit.visibility = View.GONE
        }
        btn_edit.setOnClickListener { radioGroup.visibility = View.VISIBLE }
    }
}

package com.daixu.dagger.demo.view.dev

import android.app.AlertDialog
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.TextUtils
import android.view.View
import android.widget.Toast
import butterknife.ButterKnife
import com.daixu.dagger.demo.R
import com.daixu.dagger.demo.ToDoApplication
import com.daixu.dagger.demo.common.Constant
import com.daixu.dagger.demo.common.PreferenceKeys.*
import com.daixu.dagger.demo.utils.RxSPTool
import com.daixu.dagger.demo.utils.WeakHandler
import kotlinx.android.synthetic.main.activity_developer.*
import timber.log.Timber

class DeveloperActivity : AppCompatActivity() {

    private val mHandler = WeakHandler()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_developer)
        ButterKnife.bind(this)

        var url = RxSPTool.getString(this@DeveloperActivity, SERVER_URL)
        if (TextUtils.isEmpty(url)) {
            url = Constant.Url.API_SERVER_URL
        }
        tv_server_address.text = url

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
        btn_edit.setOnClickListener {
            // radioGroup.visibility = View.VISIBLE
            val items = resources.getStringArray(R.array.server_url)
            val builder = AlertDialog.Builder(this)
            builder.setTitle("切换地址")
            builder.setItems(items) { dialog, which ->
                dialog.dismiss()
                val item = items[which];
                Timber.tag("radioGroup").e(item)
                RxSPTool.putString(this@DeveloperActivity, SERVER_URL, item)

                RxSPTool.clearPreference(this@DeveloperActivity, USER_ID)
                RxSPTool.clearPreference(this@DeveloperActivity, TOKEN)
                Toast.makeText(this@DeveloperActivity, "程序将退出，需重新登录", Toast.LENGTH_SHORT).show()

                mHandler.postDelayed({
                    ToDoApplication.getInstance().exitApp()
                }, 1000)
            }
            builder.create().show()
        }
    }

    override fun onDestroy() {
        mHandler.removeCallbacksAndMessages(null)
        super.onDestroy()
    }
}

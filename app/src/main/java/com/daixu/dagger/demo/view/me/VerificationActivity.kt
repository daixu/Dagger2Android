package com.daixu.dagger.demo.view.me

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.inputmethod.EditorInfo
import com.daixu.dagger.demo.R
import com.daixu.dagger.demo.common.PreferenceKeys.IS_DEV
import com.daixu.dagger.demo.utils.RxSPTool
import kotlinx.android.synthetic.main.activity_verification.*

class VerificationActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_verification)

        val pwd = edit_pwd.text

        edit_pwd!!.setOnEditorActionListener { v, actionId, event ->
            when (actionId) {
                EditorInfo.IME_ACTION_DONE -> {
                    if (pwd.toString() == "1010") {
                        RxSPTool.putBoolean(this@VerificationActivity, IS_DEV, true)
                        finish()
                    }
                }
                else -> {
                }
            }
            false
        }
    }
}

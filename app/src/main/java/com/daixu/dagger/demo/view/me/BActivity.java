package com.daixu.dagger.demo.view.me;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.daixu.dagger.demo.R;
import com.daixu.dagger.demo.utils.RxSPTool;
import com.daixu.dagger.demo.utils.WeakHandler;
import com.daixu.dagger.demo.view.BaseActivity;
import com.daixu.dagger.demo.view.login.LoginActivity;

import java.lang.ref.WeakReference;

import static com.daixu.dagger.demo.common.PreferenceKeys.IS_DEV;

public class BActivity extends BaseActivity {

    private WeakHandler mHandler = new WeakHandler();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_verification);

        final EditText editText = findViewById(R.id.edit_pwd);
        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                switch (actionId) {
                    case EditorInfo.IME_ACTION_GO:
                    case EditorInfo.IME_ACTION_DONE: {
                        if (editText.getText().toString().equals("1010")) {
                            RxSPTool.putBoolean(BActivity.this, IS_DEV, true);
                            finish();
                        }
                    }
                    break;
                    default:
                        break;
                }
                return false;
            }
        });

        editText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editText.setVisibility(View.VISIBLE);
            }
        });

        final String[] items = getResources().getStringArray(R.array.server_url);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("切换地址");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                Toast.makeText(BActivity.this, items[which], Toast.LENGTH_SHORT).show();
            }
        });

        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(BActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        }, 3000);
    }

}

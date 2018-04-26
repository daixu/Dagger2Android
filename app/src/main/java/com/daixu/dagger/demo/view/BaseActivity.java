package com.daixu.dagger.demo.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.daixu.dagger.demo.R;
import com.daixu.dagger.demo.ToDoApplication;
import com.jaeger.library.StatusBarUtil;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

/**
 * Created by 32422 on 2017/10/11.
 */

public abstract class BaseActivity extends RxAppCompatActivity {

    protected Button sBackBtn;

    protected View mViewLine;

    protected ImageView mBackImg;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ToDoApplication.getInstance().addActivity(this);
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        setStatusBar();
    }

    @Override
    protected void onDestroy() {
        // 将本对象从ActivityStack中移除
        ToDoApplication.getInstance().removeActivity(this);
        super.onDestroy();
    }

    /*******************************************************
     * 说明：快速设置标题名，标题组件的的id必须为text_title;
     ********************************************************/
    protected void setTitleText(CharSequence text) {
        TextView textView = findViewById(R.id.tv_title);
        if (textView != null) {
            textView.setText(text);
        }
    }

    /*******************************************************
     * 说明：快速设置标题名，标题组件的的id必须为text_title;
     * @param resId
     ********************************************************/
    protected void setTitleText(int resId) {
        TextView textView = findViewById(R.id.tv_title);
        if (textView != null) {
            textView.setText(resId);
        }
    }

    protected void showBackButton() {
        sBackBtn = findViewById(R.id.btn_back);
        if (sBackBtn != null) {
            sBackBtn.setVisibility(View.VISIBLE);
        }
    }

    protected void showBackImg() {
        mBackImg = findViewById(R.id.img_back);
        if (null != mBackImg) {
            mBackImg.setVisibility(View.VISIBLE);
        }
    }

    protected void showTitleLine() {
        mViewLine = findViewById(R.id.view_title_line);
        if (null != mViewLine) {
            mViewLine.setVisibility(View.VISIBLE);
        }
    }

    public void doBack(View v) {
        finish();
    }

    protected void setStatusBar() {
        StatusBarUtil.setColor(this, ContextCompat.getColor(this, R.color.colorPrimaryDark));
    }
}

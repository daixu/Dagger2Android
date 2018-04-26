package com.daixu.dagger.demo.view.main;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ShortcutInfo;
import android.content.pm.ShortcutManager;
import android.graphics.drawable.Icon;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.daixu.dagger.demo.R;
import com.daixu.dagger.demo.view.BaseActivity;
import com.daixu.dagger.demo.view.home.HomeFragment;
import com.daixu.dagger.demo.view.me.AboutActivity;
import com.daixu.dagger.demo.view.me.MeFragment;
import com.daixu.dagger.demo.view.shopcart.ShoppingCartFragment;
import com.daixu.dagger.demo.view.todo.TodoFragment;

import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import dagger.android.AndroidInjection;
import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;
import timber.log.Timber;

import static com.daixu.dagger.demo.common.Constant.FinishActivity.RECEIVER_ACTION_FINISH;
import static java.lang.String.format;

public class MainActivity extends BaseActivity implements BottomNavigationBar.OnTabSelectedListener, HasSupportFragmentInjector {

    private BottomNavigationBar bottomNavigationBar;
    private HomeFragment mHomeFragment;
    private TodoFragment mTodoFragment;
    private ShoppingCartFragment mShoppingCartFragment;
    private MeFragment mMeFragment;

    private FragmentManager fm;

    @Inject
    DispatchingAndroidInjector<Fragment> supportFragmentInjector;

    private long mExitTime;

    private FinishActivityReceiver mReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fm = getSupportFragmentManager();
        initBottomNavigationBar();

        setDefaultFragment();

        initDynamicShortcuts();

        mReceiver = new FinishActivityReceiver();
        registerFinishReceiver();
    }

    private void registerFinishReceiver() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(RECEIVER_ACTION_FINISH);
        registerReceiver(mReceiver, intentFilter);
    }

    private class FinishActivityReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            //根据需求添加自己需要关闭页面的action
            if (RECEIVER_ACTION_FINISH.equals(intent.getAction())) {
                finish();
            }
        }
    }

    @Override
    public AndroidInjector<Fragment> supportFragmentInjector() {
        return supportFragmentInjector;
    }

    /**
     * 设置默认的
     */
    private void setDefaultFragment() {
        if (mHomeFragment == null) {
            mHomeFragment = HomeFragment.newInstance("首页");
        }
        showFragment(mHomeFragment);
    }

    private void initBottomNavigationBar() {
        bottomNavigationBar = findViewById(R.id.bottomNavigationBar);
        bottomNavigationBar.clearAll();

        bottomNavigationBar.setMode(BottomNavigationBar.MODE_FIXED);
        bottomNavigationBar.setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_STATIC);

        bottomNavigationBar
                .addItem(new BottomNavigationItem(R.mipmap.home_nav_home_click, "首页").setActiveColorResource(R.color.normal_text_color)
                        .setInActiveColor("#999999").setInactiveIconResource(R.mipmap.home_nav_home))
                .addItem(new BottomNavigationItem(R.mipmap.home_nav_listing_click, "常用清单").setActiveColorResource(R.color.normal_text_color)
                        .setInActiveColor("#999999").setInactiveIconResource(R.mipmap.home_nav_listing))
                .addItem(new BottomNavigationItem(R.mipmap.home_nav_shopping_cart_click, "购物车").setActiveColorResource(R.color.normal_text_color)
                        .setInActiveColor("#999999").setInactiveIconResource(R.mipmap.home_nav_shopping_cart))
                .addItem(new BottomNavigationItem(R.mipmap.home_nav_mine_click, "我的").setActiveColorResource(R.color.normal_text_color)
                        .setInActiveColor("#999999").setInactiveIconResource(R.mipmap.home_nav_mine))
                .setFirstSelectedPosition(0)
                .initialise();

        bottomNavigationBar.setTabSelectedListener(this);
    }

    @Override
    public void onTabSelected(int position) {
        Timber.tag("Dagger2").e(format("onTabSelected=%s", position));
        switch (position) {
            case 0:
                if (mHomeFragment == null) {
                    mHomeFragment = HomeFragment.newInstance(getString(R.string.title_home));
                }
                showFragment(mHomeFragment);
                break;
            case 1:
                if (mTodoFragment == null) {
                    mTodoFragment = TodoFragment.newInstance(getString(R.string.title_todo));
                }
                showFragment(mTodoFragment);
                break;
            case 2:
                if (mShoppingCartFragment == null) {
                    mShoppingCartFragment = ShoppingCartFragment.newInstance(getString(R.string.shopping_cart));
                }
                showFragment(mShoppingCartFragment);
                break;
            case 3:
                if (mMeFragment == null) {
                    mMeFragment = MeFragment.newInstance("我的");
                }
                showFragment(mMeFragment);
                break;
            default:
                break;
        }
        // 事务提交
//        transaction.commit();
    }

    private void showFragment(Fragment fragment) {
        if (!fragment.isVisible()) {
            @SuppressLint("RestrictedApi") List<Fragment> fragList = fm.getFragments();
            FragmentTransaction transaction = fm.beginTransaction();
            if (fragList != null && fragList.contains(fragment)) {
                for (Fragment frag : fragList) {
                    if (frag.equals(fragment) && frag.isHidden()) {
                        transaction.show(frag);
                    } else {
                        transaction.hide(frag);
                    }
                }
                transaction.commitAllowingStateLoss();
            } else {
                if (fragList != null && fragList.size() > 0) {
                    for (Fragment frag : fragList) {
                        transaction.hide(frag);
                    }
                }
                transaction.add(R.id.bottom_nav_content, fragment);
                transaction.commitAllowingStateLoss();
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (mReceiver != null) {
            unregisterReceiver(mReceiver);
        }
    }

    @Override
    public void onTabUnselected(int position) {
        Timber.tag("Dagger2").e(format("onTabUnselected=%s", position));
    }

    @Override
    public void onTabReselected(int position) {
        Timber.tag("Dagger2").e(format("onTabReselected=%s", position));
    }

    private void initDynamicShortcuts() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N_MR1) {
            return;
        }
        // 创建动态快捷方式的第一步，创建ShortcutManager
        ShortcutManager shortcutManager = getSystemService(ShortcutManager.class);
        // 构建动态快捷方式的详细信息
        ShortcutInfo myWallet = new ShortcutInfo.Builder(this, "my_wallet")
                .setShortLabel("我的钱包")
                .setLongLabel("我的钱包")
                .setIcon(Icon.createWithResource(this, R.mipmap.ic_launcher))
                .setIntent(new Intent(Intent.ACTION_VIEW, Uri.EMPTY, this, AboutActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK))
                .build();

        ShortcutInfo myOrder = new ShortcutInfo.Builder(this, "my_order")
                .setShortLabel("我的订单")
                .setLongLabel("我的订单")
                .setIcon(Icon.createWithResource(this, R.mipmap.ic_launcher))
                .setIntent(new Intent(Intent.ACTION_MAIN, Uri.EMPTY, this, MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK))
                .build();
        // 为ShortcutManager设置动态快捷方式集合
        if (null != shortcutManager) {
            shortcutManager.setDynamicShortcuts(Arrays.asList(myWallet, myOrder));
        }
    }
}

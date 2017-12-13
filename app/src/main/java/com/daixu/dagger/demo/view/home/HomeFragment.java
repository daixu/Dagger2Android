package com.daixu.dagger.demo.view.home;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.daixu.dagger.demo.R;
import com.daixu.dagger.demo.bean.BannerResp;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.AndroidSupportInjection;
import dagger.android.support.HasSupportFragmentInjector;
import timber.log.Timber;

import static java.lang.String.format;

public class HomeFragment extends Fragment implements HomeContract.View, HasSupportFragmentInjector {
    private static final String ARG_PARAM1 = "param1";
    @Inject
    DispatchingAndroidInjector<Fragment> childFragmentInjector;
    @Inject
    HomeContract.Presenter mPresenter;
    TextView mTvHome;
    public HomeFragment() {
    }

    public static HomeFragment newInstance(String param1) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        AndroidSupportInjection.inject(this);
        super.onAttach(context);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mTvHome = view.findViewById(R.id.tv_home);
        mPresenter.takeView(this);
        mPresenter.loadHomeBanner();
    }

    @Override
    public AndroidInjector<Fragment> supportFragmentInjector() {
        return childFragmentInjector;
    }

    @Override
    public boolean isActive() {
        return false;
    }

    @Override
    public void updateFailure() {
        Timber.tag("Dagger2").v("updateFailure");
    }

    @Override
    public void updateBanner(BannerResp resp) {
        if (null != resp.data) {
            for (int i = 0; i < resp.data.size(); i++) {
                Timber.tag("Dagger2").v(format("userInfo=%s", resp.data.get(i).image));
            }
        }
    }
}

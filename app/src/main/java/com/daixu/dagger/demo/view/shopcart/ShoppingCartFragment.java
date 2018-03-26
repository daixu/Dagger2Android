package com.daixu.dagger.demo.view.shopcart;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.daixu.dagger.demo.R;
import com.daixu.dagger.demo.bean.GetShopCartResp;

import javax.inject.Inject;

import dagger.android.support.AndroidSupportInjection;
import timber.log.Timber;

import static java.lang.String.format;

public class ShoppingCartFragment extends Fragment implements ShoppingCartContract.View {
    private static final String ARG_PARAM1 = "param1";

    @Inject
    ShoppingCartContract.Presenter mPresenter;

    public static ShoppingCartFragment newInstance(String param1) {
        ShoppingCartFragment fragment = new ShoppingCartFragment();
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
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // cdbe0737-60e3-4747-a523-7f8a76e0b991

        init();
    }

    private void init() {
        mPresenter.takeView(this);
        mPresenter.loadShopCartList("040b3aa4-67de-4f12-8412-83de8cf4b784");
    }

    @Override
    public boolean isActive() {
        return false;
    }

    @Override
    public void loadShopCartFailure() {
        Timber.tag("Dagger2").w("loadShopCartFailure");
    }

    @Override
    public void loadShopCartSuccess(GetShopCartResp resp) {
        if (null != resp.data) {
            Timber.tag("Dagger2").w(format("%s", resp));
        }
    }
}

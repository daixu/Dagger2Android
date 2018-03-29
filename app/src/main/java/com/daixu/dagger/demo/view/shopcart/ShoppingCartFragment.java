package com.daixu.dagger.demo.view.shopcart;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.daixu.dagger.demo.R;
import com.daixu.dagger.demo.bean.GetShopCartResp;
import com.daixu.dagger.demo.utils.RxSPTool;
import com.trello.rxlifecycle2.LifecycleTransformer;
import com.trello.rxlifecycle2.components.support.RxFragment;

import javax.inject.Inject;

import dagger.android.support.AndroidSupportInjection;
import timber.log.Timber;

import static com.daixu.dagger.demo.common.PreferenceKeys.USER_ID;
import static java.lang.String.format;

public class ShoppingCartFragment extends RxFragment implements ShoppingCartContract.View {
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
        String userId = RxSPTool.getString(this.getActivity(), USER_ID);
        mPresenter.loadShopCartList(userId);
    }

    @Override
    public boolean isActive() {
        return false;
    }

    @Override
    public <T> LifecycleTransformer<T> bindToLife() {
        return this.<T>bindToLifecycle();
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

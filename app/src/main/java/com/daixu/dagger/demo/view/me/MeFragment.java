package com.daixu.dagger.demo.view.me;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.daixu.dagger.demo.R;
import com.daixu.dagger.demo.utils.RxSPTool;
import com.daixu.dagger.demo.view.dev.DeveloperActivity;
import com.daixu.dagger.demo.view.main.AnimActivity;
import com.daixu.dagger.demo.view.order.MyOrderActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static com.daixu.dagger.demo.common.PreferenceKeys.IS_DEV;

public class MeFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    Unbinder unbinder;
    @BindView(R.id.btn_dev)
    Button mBtnDev;

    public MeFragment() {
    }

    public static MeFragment newInstance(String param1) {
        MeFragment fragment = new MeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_me, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        boolean isDev = RxSPTool.getBoolean(this.getActivity(), IS_DEV);
        if (isDev) {
            mBtnDev.setVisibility(View.VISIBLE);
        } else {
            mBtnDev.setVisibility(View.GONE);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.tv_order, R.id.tv_about, R.id.btn_dev, R.id.btn_switch})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_order: {
                Intent intent = new Intent(this.getActivity(), MyOrderActivity.class);
                startActivity(intent);
            }
            break;
            case R.id.tv_about: {
                Intent intent = new Intent(this.getActivity(), AboutActivity.class);
                startActivity(intent);
            }
            break;
            case R.id.btn_dev: {
                Intent intent = new Intent(this.getActivity(), DeveloperActivity.class);
                startActivity(intent);
            }
            break;
            case R.id.btn_switch: {
                Intent intent = new Intent(this.getActivity(), AnimActivity.class);
                startActivity(intent);
            }
            break;
            default:
                break;
        }
    }
}

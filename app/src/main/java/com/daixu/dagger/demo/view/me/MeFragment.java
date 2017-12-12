package com.daixu.dagger.demo.view.me;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.daixu.dagger.demo.R;
import com.daixu.dagger.demo.bean.BannerResp;
import com.daixu.dagger.demo.view.home.HomeContract;

/**
 * A simple {@link Fragment} subclass.
 */
public class MeFragment extends Fragment implements HomeContract.View {
    private static final String ARG_PARAM1 = "param1";

    public MeFragment() {
        // Required empty public constructor
    }

    public static MeFragment newInstance(String param1) {
        MeFragment fragment = new MeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public boolean isActive() {
        return false;
    }

    @Override
    public void updateFailure() {

    }

    @Override
    public void updateBanner(BannerResp resp) {

    }
}

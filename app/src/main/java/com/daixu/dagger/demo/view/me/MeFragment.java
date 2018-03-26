package com.daixu.dagger.demo.view.me;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.daixu.dagger.demo.R;
import com.daixu.dagger.demo.view.order.MyOrderActivity;

public class MeFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";

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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_me, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        view.findViewById(R.id.tv_order).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MeFragment.this.getActivity(), MyOrderActivity.class);
                startActivity(intent);
            }
        });
        view.findViewById(R.id.tv_about).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MeFragment.this.getActivity(), AboutActivity.class);
                startActivity(intent);
            }
        });
    }
}

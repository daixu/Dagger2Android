package com.daixu.dagger.demo.view.todo;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.daixu.dagger.demo.R;
import com.daixu.dagger.demo.bean.GetUserTodoResp;

import javax.inject.Inject;

import dagger.android.support.AndroidSupportInjection;

public class TodoFragment extends Fragment implements TodoContract.View {
    private static final String ARG_PARAM1 = "param1";
    @Inject
    TodoContract.Presenter mPresenter;

    public static TodoFragment newInstance(String param1) {
        TodoFragment fragment = new TodoFragment();
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
        mPresenter.takeView(this);
        mPresenter.loadUserTodoList("cdbe0737-60e3-4747-a523-7f8a76e0b991", "");
    }

    @Override
    public boolean isActive() {
        return false;
    }

    @Override
    public void loadUserTodoFailure() {
        Log.e("TodoFragment", "loadUserTodoFailure");
    }

    @Override
    public void loadUserTodoSuccess(GetUserTodoResp resp) {
        Log.e("TodoFragment", "resp=" + resp);
    }
}

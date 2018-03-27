package com.daixu.dagger.demo.view.order;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.daixu.dagger.demo.R;
import com.daixu.dagger.demo.bean.BaseResp;
import com.daixu.dagger.demo.bean.RxBusEvent;
import com.daixu.dagger.demo.utils.RxBus;
import com.jkb.fragment.rigger.annotation.LazyLoad;
import com.jkb.fragment.rigger.annotation.Puppet;

import javax.inject.Inject;

import dagger.android.support.AndroidSupportInjection;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import timber.log.Timber;

@Puppet
@LazyLoad
public class MyOrderFragment extends Fragment implements MyOrderContract.View{
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private int position = 0;
    private String mParam2;

    @Inject
    MyOrderContract.Presenter mPresenter;

    private Disposable mDisposable;

    public MyOrderFragment() {
    }

    public static MyOrderFragment newInstance(int param1, String param2) {
        MyOrderFragment fragment = new MyOrderFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            position = getArguments().getInt(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    private void init() {
        mPresenter.getUserOrders("040b3aa4-67de-4f12-8412-83de8cf4b784", 1, position);
    }

    public void onLazyLoadViewCreated(Bundle savedInstanceState) {
        init();
    }

    @Override
    public void onAttach(Context context) {
        AndroidSupportInjection.inject(this);
        super.onAttach(context);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_my_order, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mPresenter.takeView(this);
        // init();

        view.findViewById(R.id.btn_detail).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyOrderFragment.this.getActivity(), OrderDetailActivity.class);
                intent.putExtra("position", position);
                startActivity(intent);
            }
        });

        mDisposable = RxBus.get().toObservable(RxBusEvent.class)
                .subscribe(new Consumer<RxBusEvent>() {
                    @Override
                    public void accept(@NonNull RxBusEvent event) throws Exception {
                        if (RxBusEvent.RxBusType.REFRESH.equals(event.type)) {
                            int pos = event.position;
                            if (pos == position) {
                                init();
                            }
                        }
                    }
                });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        if (null != mDisposable && mDisposable.isDisposed()) {
            mDisposable.dispose();
        }
    }

    @Override
    public boolean isActive() {
        return false;
    }

    @Override
    public void updateUserOrders(BaseResp resp) {
        Timber.tag("updateUserOrders").e("resp=" + resp);
    }

    @Override
    public void getUserOrdersFailure() {
        Timber.tag("getUserOrdersFailure").e("getUserOrdersFailure");
    }
}

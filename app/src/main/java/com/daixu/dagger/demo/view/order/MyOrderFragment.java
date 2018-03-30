package com.daixu.dagger.demo.view.order;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.daixu.dagger.demo.R;
import com.daixu.dagger.demo.adapter.MyOrderAdapter;
import com.daixu.dagger.demo.bean.GetUserOrdersResp;
import com.daixu.dagger.demo.bean.OrderCommodityBean;
import com.daixu.dagger.demo.bean.RxBusEvent;
import com.daixu.dagger.demo.utils.RxBus;
import com.daixu.dagger.demo.utils.RxSPTool;
import com.trello.rxlifecycle2.LifecycleTransformer;
import com.trello.rxlifecycle2.components.support.RxFragment;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import dagger.android.support.AndroidSupportInjection;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import timber.log.Timber;

import static com.daixu.dagger.demo.common.PreferenceKeys.USER_ID;

/**
 * Created by 32422 on 2018/3/27.
 */
public class MyOrderFragment extends RxFragment implements SwipeRefreshLayout.OnRefreshListener, MyOrderContract.View {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    @BindView(R.id.recycler_commodity)
    RecyclerView mRecyclerCommodity;
    Unbinder unbinder;

    private int position;

    private int pageNum = 1;
    private boolean isEnd;

    private MyOrderAdapter mAdapter;
    private List<OrderCommodityBean> mList = new ArrayList<>();

    private SwipeRefreshLayout mSwipeRefreshLayout;

    @Inject
    MyOrderPresenter mPresenter;

    public String mParam2;

    private Disposable mDisposable;

    public MyOrderFragment() {
        // Required empty public constructor
    }

    public static MyOrderFragment newInstance(int param1, String param2) {
        MyOrderFragment fragment = new MyOrderFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

//    public void onLazyLoadViewCreated(Bundle savedInstanceState) {
//        initData();
//    }

    @Override
    public void onAttach(Context context) {
        AndroidSupportInjection.inject(this);
        super.onAttach(context);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            position = getArguments().getInt(ARG_PARAM1, -1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_order, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mAdapter = new MyOrderAdapter(R.layout.item_my_order, mList);
        mRecyclerCommodity.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        mRecyclerCommodity.setAdapter(mAdapter);

        mSwipeRefreshLayout = view.findViewById(R.id.swipeLayout);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorAccent, R.color.colorPrimary, R.color.aid_prompt_color, R.color.colorBackground);

        mPresenter.takeView(this);
        initData();

        View emptyView = LayoutInflater.from(this.getActivity()).inflate(R.layout.layout_empty_view, null);
        mAdapter.setEmptyView(emptyView);

        mDisposable = RxBus.get().toObservable(RxBusEvent.class)
                .subscribe(new Consumer<RxBusEvent>() {
                    @Override
                    public void accept(@NonNull RxBusEvent event) throws Exception {
                        if (RxBusEvent.RxBusType.REFRESH.equals(event.type)) {
                            int pos = event.position;
                            if (pos == position) {
                                initData();
                            }
                        }
                    }
                });

        mAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                mSwipeRefreshLayout.setEnabled(false);
                mRecyclerCommodity.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mSwipeRefreshLayout.setEnabled(false);
                        if (isEnd) {
                            //数据全部加载完毕
                            mAdapter.loadMoreEnd();
                            mSwipeRefreshLayout.setEnabled(true);
                        } else {
                            //成功获取更多数据
                            String userId = RxSPTool.getString(MyOrderFragment.this.getActivity(), USER_ID);
                            mPresenter.getUserOrders(userId, pageNum, position);
                        }
                    }
                }, 200);
            }
        }, mRecyclerCommodity);
    }

    private void initData() {
        pageNum = 1;
        String userId = RxSPTool.getString(MyOrderFragment.this.getActivity(), USER_ID);
        mPresenter.getUserOrders(userId, pageNum, position);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        if (null != mPresenter) {
            mPresenter.unSubscribe();
        }

        if (null != mDisposable && mDisposable.isDisposed()) {
            mDisposable.dispose();
        }

        try {
            Field childFragmentManager = Fragment.class.getDeclaredField("mChildFragmentManager");
            childFragmentManager.setAccessible(true);
            childFragmentManager.set(this, null);
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onRefresh() {
        mAdapter.setEnableLoadMore(false);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                pageNum = 1;
                String userId = RxSPTool.getString(MyOrderFragment.this.getActivity(), USER_ID);
                mPresenter.getUserOrders(userId, pageNum, position);
            }
        }, 200);
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
    public void updateUserOrders(GetUserOrdersResp resp) {
        Timber.tag("updateUserOrders").e("resp=" + resp);
        mSwipeRefreshLayout.setRefreshing(false);
        if (null != resp.list && resp.list.size() > 0) {
            if (pageNum == 1) {
                mList.clear();
            }
            mList.addAll(resp.list);
            if (pageNum >= resp.pages) {
                isEnd = true;
            } else {
                isEnd = false;
                pageNum += 1;
            }
            mAdapter.setNewData(mList);
            mAdapter.loadMoreComplete();
            mSwipeRefreshLayout.setEnabled(true);
        }
    }

    @Override
    public void getUserOrdersFailure() {
        Timber.tag("getUserOrdersFailure").e("getUserOrdersFailure");
        mSwipeRefreshLayout.setRefreshing(false);
    }
}

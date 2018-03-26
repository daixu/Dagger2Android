package com.daixu.dagger.demo.view.order;

import com.daixu.dagger.demo.bean.BaseResp;
import com.daixu.dagger.demo.view.BasePresenter;
import com.daixu.dagger.demo.view.BaseView;

/**
 * Created by 32422 on 2018/3/23.
 */

public interface MyOrderContract {

    interface Presenter extends BasePresenter {
        void takeView(MyOrderContract.View view);

        void getUserOrders(String userId, int pageNum, int orderStatus);
    }

    interface View extends BaseView<Presenter> {

        void updateUserOrders(BaseResp reso);

        void getUserOrdersFailure();
    }
}

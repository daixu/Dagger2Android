package com.daixu.dagger.demo.view.shopcart;

import com.daixu.dagger.demo.bean.GetShopCartResp;
import com.daixu.dagger.demo.view.BasePresenter;
import com.daixu.dagger.demo.view.BaseView;

/**
 * Created by 32422 on 2017/12/9.
 */

public interface ShoppingCartContract {

    interface Presenter extends BasePresenter {
        void takeView(ShoppingCartContract.View view);

        void loadShopCartList(String userId);
    }

    interface View extends BaseView<Presenter> {

        void loadShopCartFailure();

        void loadShopCartSuccess(GetShopCartResp resp);
    }
}

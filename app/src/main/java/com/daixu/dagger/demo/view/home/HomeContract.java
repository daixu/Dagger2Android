package com.daixu.dagger.demo.view.home;

import com.daixu.dagger.demo.bean.BannerResp;
import com.daixu.dagger.demo.view.BasePresenter;
import com.daixu.dagger.demo.view.BaseView;

/**
 * Created by 32422 on 2017/12/9.
 */

public interface HomeContract {

    interface Presenter extends BasePresenter {
        void takeView(HomeContract.View view);

        void loadHomeBanner();
    }

    interface View extends BaseView<Presenter> {

        void updateFailure();

        void updateBanner(BannerResp resp);
    }
}

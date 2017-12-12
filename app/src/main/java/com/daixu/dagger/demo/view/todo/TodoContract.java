package com.daixu.dagger.demo.view.todo;

import com.daixu.dagger.demo.bean.GetUserTodoResp;
import com.daixu.dagger.demo.view.BasePresenter;
import com.daixu.dagger.demo.view.BaseView;

/**
 * Created by 32422 on 2017/12/9.
 */

public interface TodoContract {

    interface Presenter extends BasePresenter {
        void takeView(TodoContract.View view);

        void loadUserTodoList(String userId, String categoryId);
    }

    interface View extends BaseView<Presenter> {

        void loadUserTodoFailure();

        void loadUserTodoSuccess(GetUserTodoResp resp);
    }
}

package com.daixu.dagger.demo.view;

import com.trello.rxlifecycle2.LifecycleTransformer;

public interface BaseView<T> {

    boolean isActive();

    <T> LifecycleTransformer<T> bindToLife();
}

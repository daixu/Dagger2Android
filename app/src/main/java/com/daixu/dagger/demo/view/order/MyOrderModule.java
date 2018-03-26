package com.daixu.dagger.demo.view.order;

import com.daixu.dagger.demo.di.ActivityScoped;
import com.daixu.dagger.demo.di.FragmentScoped;

import dagger.Binds;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class MyOrderModule {

    @FragmentScoped
    @ContributesAndroidInjector
    abstract MyOrderFragment myOrderFragment();

    @ActivityScoped
    @Binds
    abstract MyOrderContract.Presenter myOrderPresenter(MyOrderPresenter presenter);
}

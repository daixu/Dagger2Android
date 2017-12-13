package com.daixu.dagger.demo.view.shopcart;

import com.daixu.dagger.demo.di.ActivityScoped;
import com.daixu.dagger.demo.di.FragmentScoped;

import dagger.Binds;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ShoppingCartModule {

    @FragmentScoped
    @ContributesAndroidInjector
    abstract ShoppingCartFragment shoppingCartFragment();

    @ActivityScoped
    @Binds
    abstract ShoppingCartContract.Presenter shoppingCartPresenter(ShoppingCartPresenter presenter);
}
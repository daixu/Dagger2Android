package com.daixu.dagger.demo.view.todo;

import com.daixu.dagger.demo.di.ActivityScoped;
import com.daixu.dagger.demo.di.FragmentScoped;

import dagger.Binds;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class TodoModule {

    @FragmentScoped
    @ContributesAndroidInjector
    abstract TodoFragment todoFragment();

    @ActivityScoped
    @Binds
    abstract TodoContract.Presenter todoPresenter(TodoPresenter presenter);
}
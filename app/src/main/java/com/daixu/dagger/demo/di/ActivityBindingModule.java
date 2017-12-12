package com.daixu.dagger.demo.di;

import com.daixu.dagger.demo.view.home.HomeModule;
import com.daixu.dagger.demo.view.login.LoginActivity;
import com.daixu.dagger.demo.view.login.LoginModule;
import com.daixu.dagger.demo.view.main.MainActivity;
import com.daixu.dagger.demo.view.todo.TodoModule;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityBindingModule {

    @ActivityScoped
    @ContributesAndroidInjector(modules = LoginModule.class)
    abstract LoginActivity loginActivity();

    @ActivityScoped
    @ContributesAndroidInjector(modules = {HomeModule.class, TodoModule.class})
    abstract MainActivity mainActivity();
}
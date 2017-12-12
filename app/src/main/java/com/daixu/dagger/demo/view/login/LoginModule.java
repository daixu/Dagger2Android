package com.daixu.dagger.demo.view.login;

import com.daixu.dagger.demo.di.ActivityScoped;
import com.daixu.dagger.demo.di.FragmentScoped;

import dagger.Binds;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Created by 32422 on 2017/12/11.
 */
@Module
public abstract class LoginModule {

    @FragmentScoped
    @ContributesAndroidInjector
    abstract LoginActivity loginActivity();

    @ActivityScoped
    @Binds
    abstract LoginContract.Presenter loginPresenter(LoginPresenter presenter);

}

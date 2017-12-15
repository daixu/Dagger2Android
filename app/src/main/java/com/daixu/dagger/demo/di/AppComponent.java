package com.daixu.dagger.demo.di;

import android.app.Application;

import com.daixu.dagger.demo.ToDoApplication;
import com.daixu.dagger.demo.data.TasksRepository;
import com.daixu.dagger.demo.net.module.ApiServiceModule;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjector;
import dagger.android.DaggerApplication;
import dagger.android.support.AndroidSupportInjectionModule;

@Singleton
@Component(modules = {
        ApplicationModule.class,
        ActivityBindingModule.class,
        ApiServiceModule.class,
        AndroidSupportInjectionModule.class})
public interface AppComponent extends AndroidInjector<DaggerApplication> {

    void inject(ToDoApplication application);

    TasksRepository getTasksRepository();

    @Override
    void inject(DaggerApplication instance);

    @Component.Builder
    interface Builder {
        @BindsInstance
        AppComponent.Builder application(Application application);

        AppComponent build();
    }
}

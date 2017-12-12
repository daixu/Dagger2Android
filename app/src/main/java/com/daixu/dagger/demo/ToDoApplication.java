package com.daixu.dagger.demo;

import com.daixu.dagger.demo.data.TasksRepository;
import com.daixu.dagger.demo.di.AppComponent;
import com.daixu.dagger.demo.di.DaggerAppComponent;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DaggerApplication;

public class ToDoApplication extends DaggerApplication {

    @Inject
    TasksRepository tasksRepository;

    private AppComponent mAppComponent;
    private static ToDoApplication sApplication;

    public static ToDoApplication getInstance() {
        return sApplication;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sApplication = this;
    }

    @Override
    protected AndroidInjector<? extends DaggerApplication> applicationInjector() {
        mAppComponent = DaggerAppComponent.builder().application(this).build();
        mAppComponent.inject(this);
        return mAppComponent;
    }

    public AppComponent getAppComponent() {
        return mAppComponent;
    }

    public TasksRepository getTasksRepository() {
        return tasksRepository;
    }

}

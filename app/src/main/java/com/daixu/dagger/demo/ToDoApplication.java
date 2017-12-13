package com.daixu.dagger.demo;

import android.support.annotation.NonNull;
import android.support.annotation.VisibleForTesting;
import android.util.Log;

import com.daixu.dagger.demo.data.TasksRepository;
import com.daixu.dagger.demo.di.AppComponent;
import com.daixu.dagger.demo.di.DaggerAppComponent;
import com.daixu.dagger.demo.net.RetrofitModule;
import com.daixu.dagger.demo.utils.FakeCrashLibrary;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DaggerApplication;
import timber.log.Timber;

public class ToDoApplication extends DaggerApplication {

    @Inject
    TasksRepository tasksRepository;

    @Inject
    RetrofitModule mRetrofitModule;

    private AppComponent mAppComponent;
    private static ToDoApplication sApplication;

    public static ToDoApplication getInstance() {
        return sApplication;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sApplication = this;

        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        } else {
            Timber.plant(new CrashReportingTree());
        }
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

    private static class CrashReportingTree extends Timber.Tree {
        @Override
        protected void log(int priority, String tag, @NonNull String message, Throwable t) {
            if (priority == Log.VERBOSE || priority == Log.DEBUG) {
                return;
            }

            FakeCrashLibrary.log(priority, tag, message);

            if (t != null) {
                if (priority == Log.ERROR) {
                    FakeCrashLibrary.logError(t);
                } else if (priority == Log.WARN) {
                    FakeCrashLibrary.logWarning(t);
                }
            }
        }
    }

    @VisibleForTesting
    public RetrofitModule getRetrofitModule() {
        return mRetrofitModule;
    }
}

package com.example.todo.di.module

import com.example.todo.di.scope.ActivityScope
import com.example.todo.presentation.activity.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityModule {
    @ActivityScope
    @ContributesAndroidInjector
    abstract fun contributeHomeActivity(): MainActivity
}
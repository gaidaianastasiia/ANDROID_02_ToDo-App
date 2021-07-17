package com.example.todo

import com.example.todo.di.component.DaggerApplicationComponent
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication

class Application: DaggerApplication() {
    override fun applicationInjector(): AndroidInjector<Application?> {
        return DaggerApplicationComponent.factory().create(this)
    }
}
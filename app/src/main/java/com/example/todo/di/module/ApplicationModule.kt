package com.example.todo.di.module

import android.content.Context
import com.example.todo.Application
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
object ApplicationModule {
    @Provides
    @Singleton
    @JvmStatic
    fun context(app: Application): Context {
        return app.applicationContext
    }
}
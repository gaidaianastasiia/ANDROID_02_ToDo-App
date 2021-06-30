package com.example.todo.di.component

import com.example.todo.Application
import com.example.todo.di.module.*
import com.example.todo.presentation.activity.MainActivity
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Component(modules = [
    ActivityModule::class,
    ApplicationModule::class,
    AndroidSupportInjectionModule::class,
    ViewModelFactoryModule::class,
    RepositoryModule::class,
    StorageModule::class
])
@Singleton
interface ApplicationComponent : AndroidInjector<Application> {
    @Component.Factory
    interface Factory : AndroidInjector.Factory<Application>
}
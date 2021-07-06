package com.example.todo

import android.util.Log
import com.example.todo.di.component.DaggerApplicationComponent
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication

//Когда создаем свою реализацию Application, обязательно в manifest добавить
//имя класса application: android:name=".Application"

class Application: DaggerApplication() {
    override fun applicationInjector(): AndroidInjector<Application?> {
        return DaggerApplicationComponent.factory().create(this)
    }
}
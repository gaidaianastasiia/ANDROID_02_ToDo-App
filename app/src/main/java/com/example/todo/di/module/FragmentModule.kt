package com.example.todo.di.module

import com.example.todo.di.scope.FragmentScope
import com.example.todo.presentation.fragment.example.ExampleFragment
import com.example.todo.presentation.fragment.todolist.ToDoControlsFragment
import com.example.todo.presentation.fragment.todolist.ToDoListFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentModule {
    @FragmentScope
    @ContributesAndroidInjector
    abstract fun contributeExampleFragment(): ExampleFragment

    @FragmentScope
    @ContributesAndroidInjector
    abstract fun contributeToDoListFragment(): ToDoListFragment

    @FragmentScope
    @ContributesAndroidInjector
    abstract fun contributeToDoControlsFragment(): ToDoControlsFragment
}
package com.example.todo.di.module

import com.example.todo.presentation.activity.MainViewModel
import com.example.todo.presentation.base.ViewModelAssistedFactory
import com.example.todo.presentation.fragment.todolist.ToDoControlsViewModel
import com.example.todo.presentation.fragment.todolist.ToDoDialogViewModel
import com.example.todo.presentation.fragment.todolist.ToDoListViewModel
import dagger.Binds
import dagger.Module

@Module
interface ViewModelFactoryModule {
    // >> Activities

    @Binds
    fun bindMainViewModelAssistedFactory(
        viewModelFactory: MainViewModel.Factory
    ): ViewModelAssistedFactory<MainViewModel>

    // >> Activities

    // >> Fragments

    @Binds
    fun bindToDoListViewModelAssistedFactory(
        viewModelFactory: ToDoListViewModel.Factory
    ) : ViewModelAssistedFactory<ToDoListViewModel>

    @Binds
    fun bindToDoControlsViewModelAssistedFactory(
        viewModelFactory: ToDoControlsViewModel.Factory
    ) : ViewModelAssistedFactory<ToDoControlsViewModel>

    @Binds
    fun bindToDoDialogViewModelAssistedFactory(
        viewModelFactory: ToDoDialogViewModel.Factory
    ) : ViewModelAssistedFactory<ToDoDialogViewModel>

    // >> Fragments
}
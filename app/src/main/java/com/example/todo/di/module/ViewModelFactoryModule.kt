package com.example.todo.di.module

import com.example.todo.presentation.activity.MainViewModel
import com.example.todo.presentation.base.ViewModelAssistedFactory
import com.example.todo.presentation.fragment.example.ExampleViewModel
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
    fun bindExampleViewModelAssistedFactory(
        viewModelFactory: ExampleViewModel.Factory
    ): ViewModelAssistedFactory<ExampleViewModel>

    // >> Fragments
}
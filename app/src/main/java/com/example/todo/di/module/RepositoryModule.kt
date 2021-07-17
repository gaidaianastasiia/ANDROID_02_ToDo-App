package com.example.todo.di.module

import com.example.todo.data.repositories.LocalToDoRepository
import com.example.todo.data.repositories.ToDoRepository
import dagger.Binds
import dagger.Module

@Module
interface RepositoryModule {
    @Binds
    fun bindToDoRepository(localToDoRepository: LocalToDoRepository): ToDoRepository
}
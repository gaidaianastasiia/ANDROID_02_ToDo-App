package com.example.todo.di.module

import android.content.Context
import androidx.room.Room
import com.example.todo.data.ApplicationDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class StorageModule {
    @Provides
    @Singleton
    fun provideDataBase(context: Context) =
        Room.databaseBuilder(
            context,
            ApplicationDatabase::class.java,
            "ApplicationDatabase"
        ).build()

    @Provides
    @Singleton
    fun provideToDoDao(database: ApplicationDatabase) = database.toDoDao()
}
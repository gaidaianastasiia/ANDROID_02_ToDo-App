package com.example.todo.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.todo.data.dao.ToDoDao
import com.example.todo.data.entity.ToDoData

private const val DATABASE_VERSION = 1

@Database(entities = [ToDoData::class], version = DATABASE_VERSION)
abstract class ApplicationDatabase : RoomDatabase() {
    abstract fun toDoDao(): ToDoDao
}
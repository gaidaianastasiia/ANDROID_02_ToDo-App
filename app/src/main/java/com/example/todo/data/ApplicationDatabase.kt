package com.example.todo.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.todo.data.dao.ToDoDao
import com.example.todo.data.entity.ToDoData

@Database(entities = [ToDoData::class], version = 1)
abstract class ApplicationDatabase : RoomDatabase() {
    abstract fun toDoDao(): ToDoDao
}
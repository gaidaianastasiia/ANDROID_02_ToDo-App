package com.example.todo.data.repositories

import com.example.todo.entity.ToDo

interface ToDoRepository {
    suspend fun getAll(): List<ToDo>
    suspend fun getById(id: Long): ToDo
    suspend fun insert(text: String)
    suspend fun updateText(id: Long, updatedText: String)
    suspend fun updateDoneStatus(id: Long, updatedDoneStatus: Boolean)
    suspend fun delete(id: Long)
}
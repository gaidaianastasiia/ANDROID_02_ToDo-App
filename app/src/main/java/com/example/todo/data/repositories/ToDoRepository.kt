package com.example.todo.data.repositories

import com.example.todo.entity.Result
import com.example.todo.entity.ToDo

interface ToDoRepository {
    suspend fun getAll(): Result<List<ToDo>>
    suspend fun getById(id: Long): Result<ToDo>
    suspend fun insert(text: String): Result<Unit>
    suspend fun updateText(id: Long, updatedText: String): Result<Unit>
    suspend fun updateDoneStatus(id: Long, updatedDoneStatus: Boolean): Result<Unit>
    suspend fun delete(id: Long): Result<Unit>
}
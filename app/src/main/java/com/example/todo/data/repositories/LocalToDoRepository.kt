package com.example.todo.data.repositories

import com.example.todo.data.dao.ToDoDao
import com.example.todo.data.entity.ToDoData
import com.example.todo.utils.toToDo
import javax.inject.Inject

class LocalToDoRepository @Inject constructor(
    private val toDoDao: ToDoDao
): ToDoRepository {
    override suspend fun getAll() = toDoDao.getAll().map { it.toToDo() }

    override suspend fun getById(id: Long) = toDoDao.getById(id).toToDo()

    override suspend fun insert(text: String): Boolean {
        val newTodo = ToDoData(text, false)
        toDoDao.insert(newTodo)
        return true
    }

    override suspend fun updateText(id: Long, updatedText: String): Boolean {
        toDoDao.updateText(id, updatedText)
        return true
    }

    override suspend fun updateDoneStatus(id: Long, updatedDoneStatus: Boolean): Boolean {
        toDoDao.updateDoneStatus(id, updatedDoneStatus)
        return true
    }

    override suspend fun delete(id: Long): Boolean {
        toDoDao.delete(id)
        return true
    }
}
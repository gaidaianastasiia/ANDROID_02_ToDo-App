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

    override suspend fun insert(text: String) {
        val newTodo = ToDoData(text, false)
        toDoDao.insert(newTodo)
    }

    override suspend fun updateText(id: Long, updatedText: String) {
        toDoDao.updateText(id, updatedText)
    }

    override suspend fun updateDoneStatus(id: Long, updatedDoneStatus: Boolean) {
        toDoDao.updateDoneStatus(id, updatedDoneStatus)
    }

    override suspend fun delete(id: Long) {
        toDoDao.delete(id)
    }
}
package com.example.todo.data.repositories

import com.example.todo.data.dao.ToDoDao
import com.example.todo.data.entity.ToDoData
import com.example.todo.entity.Result
import com.example.todo.entity.ToDo
import com.example.todo.utils.toToDo
import java.lang.Exception
import javax.inject.Inject

class LocalToDoRepository @Inject constructor(
    private val toDoDao: ToDoDao
) : ToDoRepository {
    override suspend fun getAll(): Result<List<ToDo>> =
        try {
            Result.Success(toDoDao.getAll().map { it.toToDo() })
        } catch (e: Exception) {
            Result.Error(e)
        }

    override suspend fun getById(id: Long): Result<ToDo> =
        try {
            Result.Success(toDoDao.getById(id).toToDo())
        } catch (e: Exception) {
            Result.Error(e)
        }

    override suspend fun insert(text: String): Result<Unit> {
        val newTodo = ToDoData(text, false)
        return try {
            val id = toDoDao.insert(newTodo)
            if (id > 0) {
                Result.Success(Unit)
            } else {
                Result.Error(Exception("Insert was failed"))
            }
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    override suspend fun updateText(id: Long, updatedText: String): Result<Unit> {
        return try {
            val updatedToDos = toDoDao.updateText(id, updatedText)
            if (updatedToDos > 0) {
                Result.Success(Unit)
            } else {
                Result.Error(Exception("Update To-Do Text was failed"))
            }
        } catch (e: Exception) {
            Result.Error(e)
        }

    }

    override suspend fun updateDoneStatus(id: Long, updatedDoneStatus: Boolean): Result<Unit> {
        return try {
            val updatedToDos = toDoDao.updateDoneStatus(id, updatedDoneStatus)
            if (updatedToDos > 0) {
                Result.Success(Unit)
            } else {
                Result.Error(Exception("Update To-Do done status was failed"))
            }
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    override suspend fun delete(id: Long): Result<Unit> {
        return try {
            val deletedToDos = toDoDao.delete(id)
            if (deletedToDos > 0) {
                Result.Success(Unit)
            } else {
                Result.Error(Exception("Delete To-Do was failed"))
            }
        } catch (e: Exception) {
            Result.Error(e)
        }
    }
}
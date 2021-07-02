package com.example.todo.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.todo.data.entity.ToDoData

@Dao
interface ToDoDao {
    @Query("SELECT * FROM ToDoData")
    suspend fun getAll(): List<ToDoData>

    @Query("SELECT * FROM ToDoData WHERE id = :id")
    suspend fun getById(id: Long): ToDoData

    @Insert
    suspend fun insert(ToDoData: ToDoData): Long

    @Query("UPDATE ToDoData SET text = :updatedText WHERE id = :id")
    suspend fun updateText(id: Long, updatedText: String): Int

    @Query("UPDATE ToDoData SET doneStatus = :updatedDoneStatus WHERE id = :id")
    suspend fun updateDoneStatus(id: Long, updatedDoneStatus: Boolean): Int

    @Query("DELETE FROM ToDoData WHERE id = :idToDelete")
    suspend fun delete(idToDelete: Long): Int
}
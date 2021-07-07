package com.example.todo.data.dao

import android.database.sqlite.SQLiteException
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.todo.data.entity.ToDoData

@Dao
interface ToDoDao {
    @Query("SELECT * FROM ToDoData")
    @Throws(SQLiteException::class)
    suspend fun getAll(): List<ToDoData>

    @Query("SELECT * FROM ToDoData WHERE id = :id")
    @Throws(SQLiteException::class)
    suspend fun getById(id: Long): ToDoData

    @Insert
    @Throws(SQLiteException::class)
    suspend fun insert(ToDoData: ToDoData): Long

    @Query("UPDATE ToDoData SET text = :updatedText WHERE id = :id")
    @Throws(SQLiteException::class)
    suspend fun updateText(id: Long, updatedText: String): Int

    @Query("UPDATE ToDoData SET doneStatus = :updatedDoneStatus WHERE id = :id")
    @Throws(SQLiteException::class)
    suspend fun updateDoneStatus(id: Long, updatedDoneStatus: Boolean): Int

    @Query("DELETE FROM ToDoData WHERE id = :idToDelete")
    @Throws(SQLiteException::class)
    suspend fun delete(idToDelete: Long): Int
}
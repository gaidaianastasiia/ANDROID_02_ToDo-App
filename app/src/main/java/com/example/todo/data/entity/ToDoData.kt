package com.example.todo.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ToDoData(
    @PrimaryKey(autoGenerate = true) val id: Long,
    @ColumnInfo val text: String,
    @ColumnInfo val doneStatus: Boolean
) {
    constructor(text: String, doneStatus: Boolean) : this(0, text, doneStatus)
}

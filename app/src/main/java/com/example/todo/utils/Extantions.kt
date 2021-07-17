package com.example.todo.utils

import com.example.todo.data.entity.ToDoData
import com.example.todo.entity.ToDo

fun ToDoData.toToDo() = ToDo(id, text, doneStatus)
package com.example.todo.domain

import com.example.todo.data.repositories.ToDoRepository
import javax.inject.Inject

class InsertToDoInteractor @Inject constructor(
    private val repository: ToDoRepository
) {
    suspend operator fun invoke(text: String) = repository.insert(text)
}
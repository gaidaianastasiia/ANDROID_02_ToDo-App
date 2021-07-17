package com.example.todo.domain

import com.example.todo.data.repositories.ToDoRepository
import javax.inject.Inject

class UpdateToDoDoneStatusInteractor @Inject constructor(
    private val repository: ToDoRepository
) {
    suspend operator fun invoke(id: Long, doneStatus: Boolean) =
        repository.updateDoneStatus(id, doneStatus)
}
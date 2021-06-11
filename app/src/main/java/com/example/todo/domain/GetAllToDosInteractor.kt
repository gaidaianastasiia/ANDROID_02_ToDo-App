package com.example.todo.domain

import com.example.todo.data.repositories.ToDoRepository
import javax.inject.Inject

class GetAllToDosInteractor @Inject constructor(
    private val repository: ToDoRepository
) {
    //Если вы хотите выполнить функцию без указания имени функции,
    // вы можете определить вызов функции как:
    suspend operator fun invoke() = repository.getAll()
}

//Вызов будет выглядеть так:
//GetAllToDosInteractor()
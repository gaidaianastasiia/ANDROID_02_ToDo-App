package com.example.todo.presentation.fragment.todolist

import androidx.lifecycle.SavedStateHandle
import com.example.todo.domain.DeleteToDoInteractor
import com.example.todo.domain.UpdateToDoTextInteractor
import com.example.todo.presentation.base.BaseViewModel
import com.example.todo.presentation.base.ViewModelAssistedFactory
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject

class ToDoControlsViewModel @AssistedInject constructor(
    @Assisted savedStateHandle: SavedStateHandle,
    @Assisted id: Long,
    private val updateToDoText: UpdateToDoTextInteractor,
    private val deleteToDo: DeleteToDoInteractor
): BaseViewModel(savedStateHandle) {
    @AssistedFactory
    interface Factory : ViewModelAssistedFactory<ToDoControlsViewModel> {
        fun create(
            savedStateHandle: SavedStateHandle,
            id: Long
        ): ToDoControlsViewModel
    }
}
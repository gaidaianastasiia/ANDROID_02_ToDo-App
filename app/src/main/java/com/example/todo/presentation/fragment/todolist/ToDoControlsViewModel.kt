package com.example.todo.presentation.fragment.todolist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import com.example.todo.entity.ToDoText
import com.example.todo.presentation.base.BaseViewModel
import com.example.todo.presentation.base.ViewModelAssistedFactory
import com.example.todo.presentation.utils.SingleLiveEvent
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject

class ToDoControlsViewModel @AssistedInject constructor(
    @Assisted savedStateHandle: SavedStateHandle,
    @Assisted private val id: Long,
    @Assisted private val text: String
) : BaseViewModel(savedStateHandle) {
    @AssistedFactory
    interface Factory : ViewModelAssistedFactory<ToDoControlsViewModel> {
        fun create(
            savedStateHandle: SavedStateHandle,
            id: Long,
            text: String
        ): ToDoControlsViewModel
    }

    private val _showEditToDoDialog = SingleLiveEvent<ToDoText>()
    val showEditToDoDialog: LiveData<ToDoText>
        get() = _showEditToDoDialog

    private val _requestDeleteToDo = MutableLiveData<Long>()
    val requestDeleteToDo: LiveData<Long>
        get() = _requestDeleteToDo

    fun onEditButtonClick() {
        _showEditToDoDialog.value = ToDoText(id, text)
    }

    fun onDeleteButtonClick() {
        _requestDeleteToDo.value = id
    }
}
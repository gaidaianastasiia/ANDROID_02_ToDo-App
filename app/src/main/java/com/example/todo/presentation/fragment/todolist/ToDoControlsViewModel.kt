package com.example.todo.presentation.fragment.todolist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import com.example.todo.presentation.base.BaseViewModel
import com.example.todo.presentation.base.BaseViewModelAssistedFactory
import com.example.todo.presentation.utils.SingleLiveEvent
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject

class ToDoControlsViewModel @AssistedInject constructor(
    @Assisted savedStateHandle: SavedStateHandle,
) : BaseViewModel(savedStateHandle) {
    @AssistedFactory
    interface Factory : BaseViewModelAssistedFactory<ToDoControlsViewModel>

    private val _showEditToDoDialog = SingleLiveEvent<Unit>()
    val showEditToDoDialog: LiveData<Unit>
        get() = _showEditToDoDialog

    private val _requestDeleteToDo = MutableLiveData<Long>()
    val requestDeleteToDo: LiveData<Long>
        get() = _requestDeleteToDo

    fun onEditButtonClick() {
        _showEditToDoDialog.call()
    }

    fun onDeleteButtonClick(id: Long) {
        _requestDeleteToDo.value = id
    }
}
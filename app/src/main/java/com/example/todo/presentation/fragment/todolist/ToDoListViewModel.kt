package com.example.todo.presentation.fragment.todolist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.example.todo.domain.GetAllToDosInteractor
import com.example.todo.domain.GetToDoByIdInteractor
import com.example.todo.entity.ToDo
import com.example.todo.presentation.base.BaseViewModel
import com.example.todo.presentation.base.BaseViewModelAssistedFactory
import com.example.todo.presentation.base.ViewModelAssistedFactory
import com.example.todo.presentation.utils.SingleLiveEvent
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ToDoListViewModel @AssistedInject constructor(
    @Assisted savedStateHandle: SavedStateHandle,
    private val getAllToDos: GetAllToDosInteractor
) : BaseViewModel(savedStateHandle) {
    @AssistedFactory
    interface Factory : BaseViewModelAssistedFactory<ToDoListViewModel>

    private val _list = MutableLiveData<List<ToDo>>()
    val list: LiveData<List<ToDo>>
        get() = _list

    private val _controls = MutableLiveData<Long>()
    val controls: LiveData<Long>
        get() = _controls

    fun onCreate() {
        viewModelScope.launch(Dispatchers.IO) {
            _list.value = getAllToDos.invoke()
        }
    }

    fun onControlsListButtonClick(id: Long) {
        _controls.value = id
    }
}
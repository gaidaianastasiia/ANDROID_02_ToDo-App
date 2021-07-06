package com.example.todo.presentation.fragment.todolist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.example.todo.domain.DeleteToDoInteractor
import com.example.todo.domain.GetAllToDosInteractor
import com.example.todo.domain.GetToDoByIdInteractor
import com.example.todo.domain.UpdateToDoDoneStatusInteractor
import com.example.todo.entity.ToDo
import com.example.todo.presentation.base.BaseViewModel
import com.example.todo.presentation.base.BaseViewModelAssistedFactory
import com.example.todo.presentation.utils.SingleLiveEvent
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ToDoListViewModel @AssistedInject constructor(
    @Assisted savedStateHandle: SavedStateHandle,
    private val getAllToDos: GetAllToDosInteractor,
    private val getToDoById: GetToDoByIdInteractor,
    private val updateDoneStatus: UpdateToDoDoneStatusInteractor,
    private val deleteToDo: DeleteToDoInteractor
) : BaseViewModel(savedStateHandle) {
    @AssistedFactory
    interface Factory : BaseViewModelAssistedFactory<ToDoListViewModel>

    private val _showEmptyState = SingleLiveEvent<Unit>()
    val showEmptyState: LiveData<Unit>
        get() = _showEmptyState

    private val _hideEmptyState = SingleLiveEvent<Unit>()
    val hideEmptyState: LiveData<Unit>
        get() = _hideEmptyState

    private val _showLoader = SingleLiveEvent<Unit>()
    val showLoader: LiveData<Unit>
        get() = _showLoader

    private val _hideLoader = SingleLiveEvent<Unit>()
    val hideLoader: LiveData<Unit>
        get() = _hideLoader

    private val _list = MutableLiveData<List<ToDo>>()
    val list: LiveData<List<ToDo>>
        get() = _list

    private val _showCreateToDoDialog = SingleLiveEvent<Unit>()
    val showCreateToDoDialog: LiveData<Unit>
        get() = _showCreateToDoDialog

    private val _controls = MutableLiveData<ToDo>()
    val controls: LiveData<ToDo>
        get() = _controls

    private val _showRecoverDeletedTodoMessage = MutableLiveData<Long>()
    val showRecoverDeletedTodoMessage: LiveData<Long>
        get() = _showRecoverDeletedTodoMessage

    fun fetchList() {
        viewModelScope.launch(Dispatchers.IO) {
            getList()
        }
    }

    fun onAddNewToDoButtonClick() {
        _showCreateToDoDialog.call()
    }

    fun onClick(id: Long, doneStatus: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            updateDoneStatus(id, !doneStatus)
            getList()
        }
    }

    fun onLongClick(id: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            val toDo = getToDoById(id)

            withContext(Dispatchers.Main) {
                _controls.value = toDo
            }
        }
    }

    fun onDeleteRequest(id: Long) {
        _showLoader.call()

        viewModelScope.launch(Dispatchers.IO) {
            val toDoList = getAllToDos.invoke().toMutableList()
            toDoList.removeAll { it.id == id }
            updateList(toDoList)

            withContext(Dispatchers.Main) {
                _showRecoverDeletedTodoMessage.value = id
            }
        }
    }

    fun delete(id: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            deleteToDo(id)
        }
    }

    private suspend fun getList() {
        viewModelScope.launch(Dispatchers.Main) {
            _showLoader.call()
        }

        val toDoList = getAllToDos.invoke()
        updateList(toDoList)
    }

    private fun updateList(list: List<ToDo>) {
        viewModelScope.launch(Dispatchers.Main) {
            if (list.isEmpty()) {
                _showEmptyState.call()
            } else {
                _hideEmptyState.call()
            }

            _hideLoader.call()
            _list.value = list
        }
    }
}

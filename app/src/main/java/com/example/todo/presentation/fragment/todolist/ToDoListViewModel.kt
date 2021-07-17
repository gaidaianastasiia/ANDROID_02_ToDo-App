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

    private val _showEmptyState = MutableLiveData<Boolean>()
    val showEmptyState: LiveData<Boolean>
        get() = _showEmptyState

    private val _showLoader = MutableLiveData<Boolean>()
    val showLoader: LiveData<Boolean>
        get() = _showLoader

    private val _toDoList = MutableLiveData<List<ToDo>>()
    val toDoList: LiveData<List<ToDo>>
        get() = _toDoList

    private val _showCreateToDoDialog = SingleLiveEvent<Unit>()
    val showCreateToDoDialog: LiveData<Unit>
        get() = _showCreateToDoDialog

    private val _controls = MutableLiveData<ToDo>()
    val controls: LiveData<ToDo>
        get() = _controls

    private val _showRecoverDeletedTodoMessage = MutableLiveData<Long>()
    val showRecoverDeletedTodoMessage: LiveData<Long>
        get() = _showRecoverDeletedTodoMessage

    private var deletedToDoId: Long? = null

    fun requestList() {
        viewModelScope.launch(Dispatchers.IO) {
            fetchList()
        }
    }

    fun onAddNewToDoButtonClick() {
        _showCreateToDoDialog.call()
    }

    fun onClick(id: Long, doneStatus: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            updateDoneStatus(id, !doneStatus)
                .doOnError { error -> onStorageError(error) }
            fetchList()
        }
    }

    fun onLongClick(id: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            getToDoById(id)
                .doOnSuccess { toDo ->
                    withContext(Dispatchers.Main) {
                        _controls.value = toDo
                    }
                }
                .doOnError { error -> onStorageError(error) }
        }
    }

    fun onDeleteRequest(id: Long) {
        _showLoader.value = true
        deletedToDoId = id

        viewModelScope.launch(Dispatchers.IO) {
            fetchList()

            withContext(Dispatchers.Main) {
                _showRecoverDeletedTodoMessage.value = id
            }
        }
    }

    fun delete(id: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            deletedToDoId = null
            deleteToDo(id).doOnError { error -> onStorageError(error) }
        }
    }

    fun cancelDelete() {
        deletedToDoId = null

        viewModelScope.launch(Dispatchers.IO) {
            fetchList()
        }
    }

    private suspend fun fetchList() {
        viewModelScope.launch(Dispatchers.Main) {
            _showLoader.value = true
        }

        getAllToDos.invoke()
            .doOnSuccess { list ->
                updateList(list)
            }
            .doOnError { error -> onStorageError(error) }
    }

    private fun updateList(list: List<ToDo>) {
        viewModelScope.launch(Dispatchers.Main) {
            val filteredList = if (deletedToDoId != null) {
                list.toMutableList().filter { it.id != deletedToDoId }
            } else null

            _showEmptyState.value = filteredList?.isEmpty() ?: list.isEmpty()
            _showLoader.value = false
            _toDoList.value = filteredList ?: list
        }
    }
}

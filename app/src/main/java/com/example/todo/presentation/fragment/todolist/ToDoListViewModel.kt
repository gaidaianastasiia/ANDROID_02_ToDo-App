package com.example.todo.presentation.fragment.todolist

import android.util.Log
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

    private val _showErrorMessage = SingleLiveEvent<Unit>()
    val showErrorMessage: LiveData<Unit>
        get() = _showErrorMessage

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
        _showLoader.call()

        viewModelScope.launch(Dispatchers.IO) {
            getAllToDos.invoke()
                .doOnSuccess { list ->
                    val updatedList = list.toMutableList()
                    updatedList.removeIf { it.id == id }
                    updateList(updatedList)
                }
                .doOnError { error -> onStorageError(error) }

            withContext(Dispatchers.Main) {
                _showRecoverDeletedTodoMessage.value = id
            }
        }
    }

    fun delete(id: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            deleteToDo(id).doOnError { error -> onStorageError(error) }
        }
    }

    private suspend fun fetchList() {
        viewModelScope.launch(Dispatchers.Main) {
            _showLoader.call()
        }

        getAllToDos.invoke()
            .doOnSuccess { list ->
                updateList(list)
            }
            .doOnError { error -> onStorageError(error) }
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

    private fun onStorageError(error: Throwable) {
        viewModelScope.launch(Dispatchers.Main) {
            _showErrorMessage.call()
            Log.e(this::class.simpleName, error.toString())
        }
    }
}

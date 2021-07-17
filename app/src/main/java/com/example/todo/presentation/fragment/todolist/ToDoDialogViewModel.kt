package com.example.todo.presentation.fragment.todolist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.example.todo.R
import com.example.todo.domain.InsertToDoInteractor
import com.example.todo.domain.UpdateToDoTextInteractor
import com.example.todo.presentation.base.BaseViewModel
import com.example.todo.presentation.base.ViewModelAssistedFactory
import com.example.todo.presentation.utils.SingleLiveEvent
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

private const val TEXT_STATE_KEY = "TEXT_STATE_KEY"

class ToDoDialogViewModel @AssistedInject constructor(
    @Assisted savedStateHandle: SavedStateHandle,
    @Assisted private val id: Long?,
    @Assisted currentText: String?,
    private val create: InsertToDoInteractor,
    private val edit: UpdateToDoTextInteractor
) : BaseViewModel(savedStateHandle) {
    @AssistedFactory
    interface Factory : ViewModelAssistedFactory<ToDoDialogViewModel> {
        fun create(
            savedStateHandle: SavedStateHandle,
            id: Long?,
            currentText: String?
        ): ToDoDialogViewModel
    }

    private val _dialogTitle = MutableLiveData<Int>()
    val dialogTitle: LiveData<Int>
        get() = _dialogTitle

    private val _positiveButtonText = MutableLiveData<Int>()
    val positiveButtonText: LiveData<Int>
        get() = _positiveButtonText

    private val _enteredText = MutableLiveData<String>()
    val enteredText: LiveData<String>
        get() = _enteredText

    private val _storageSuccessResponse = SingleLiveEvent<Unit>()
    val storageSuccessResponse: LiveData<Unit>
        get() = _storageSuccessResponse

    init {
        if (id == null) {
            _dialogTitle.value = R.string.create_to_do_dialog_title
            _positiveButtonText.value = R.string.create_to_do_dialog_positive_button
        } else {
            _dialogTitle.value = R.string.edit_to_do_dialog_title
            _positiveButtonText.value = R.string.edit_to_do_dialog_positive_button
        }

        currentText?.let {
            _enteredText.value = it
        }

        savedStateHandle.get<String>(TEXT_STATE_KEY)?.let {
            _enteredText.value = it
        }
    }

    fun saveEnteredText(text: String) {
        _enteredText.value = text
        savedStateHandle.set(TEXT_STATE_KEY, text)
    }

    fun onToDoDialogPositiveClick() {
        _enteredText.value?.let { text ->
            if (id == null) {
                createToDo(text)
            } else {
                editToDo(text)
            }
        }
    }

    private fun createToDo(text: String) {
        viewModelScope.launch(Dispatchers.IO) {
            create(text)
                .doOnSuccess {
                    withContext(Dispatchers.Main) {
                        _storageSuccessResponse.call()
                    }
                }
                .doOnError { error -> onStorageError(error) }
        }
    }

    private fun editToDo(text: String) {
        viewModelScope.launch(Dispatchers.IO) {
            id?.let { id ->
                edit(id, text)
                    .doOnSuccess {
                        withContext(Dispatchers.Main) {
                            _storageSuccessResponse.call()
                        }
                    }
                    .doOnError { error -> onStorageError(error) }
            }
        }
    }
}
package com.example.todo.presentation.fragment.todolist

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.example.todo.domain.InsertToDoInteractor
import com.example.todo.domain.UpdateToDoTextInteractor
import com.example.todo.presentation.activity.MainViewModel
import com.example.todo.presentation.base.BaseViewModel
import com.example.todo.presentation.base.BaseViewModelAssistedFactory
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
    private val create: InsertToDoInteractor,
    private val edit: UpdateToDoTextInteractor
) : BaseViewModel(savedStateHandle) {
    @AssistedFactory
    interface Factory : BaseViewModelAssistedFactory<ToDoDialogViewModel>

    private val _text = MutableLiveData<String>()
    val text: LiveData<String>
        get() = _text

    private val _storagePositiveResponse = SingleLiveEvent<Unit>()
    val storagePositiveResponse: LiveData<Unit>
    get() = _storagePositiveResponse

    init {
        savedStateHandle.get<String>(TEXT_STATE_KEY)?.let {
            _text.value = it
        }
    }

    fun onToDoDialogPositiveClick(text: String) {
        _text.value = text
        savedStateHandle.set(TEXT_STATE_KEY, text)
    }

    fun createToDo(text: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val storageResponse = create(text)

            if(storageResponse) {
                withContext(Dispatchers.Main) {
                    _storagePositiveResponse.call()
                }
            }
        }
    }

    fun editToDo(id: Long, text: String) {
//        viewModelScope.launch(Dispatchers.IO) {
//            val storageResponse = edit(id, text)
//
//            if(storageResponse) {
//                withContext(Dispatchers.Main) {
//                    _storagePositiveResponse.call()
//                }
//            }
//        }
    }
}
package com.example.todo.presentation.activity

import androidx.lifecycle.LiveData
import androidx.lifecycle.SavedStateHandle
import com.example.todo.presentation.base.BaseViewModel
import com.example.todo.presentation.base.BaseViewModelAssistedFactory
import com.example.todo.presentation.utils.SingleLiveEvent
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject

class MainViewModel @AssistedInject constructor(
    @Assisted savedStateHandle: SavedStateHandle
) : BaseViewModel(savedStateHandle) {
    @AssistedFactory
    interface Factory : BaseViewModelAssistedFactory<MainViewModel>

    //отличие SingleLiveEvent от LiveData в том что в момент подписки не вызывается метод observe
    //используется в тех случаях когда данные не меняются а происходит событие
    private val _startEvent = SingleLiveEvent<Unit>()
    val startEvent: LiveData<Unit>
        get() = _startEvent

    fun onCreate() {
        _startEvent.call()
    }
}
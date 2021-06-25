package com.example.todo.presentation.base

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel

/**
* Base [ViewModelAssistedFactory] instance which should be used as [BaseViewModel]
* factory if ViewModel does not require additional assisted constructor parameters.
*
* @param T Concrete type of ViewModel
*/
interface BaseViewModelAssistedFactory<T : ViewModel> : ViewModelAssistedFactory<T> {
    /**
    * Creates new [T] instance.
    *
    * @param savedStateHandle [SavedStateHandle] instance to store/restore state
    *
    * @return [T] ViewModel instance
    */
    fun create(savedStateHandle: SavedStateHandle): T
}
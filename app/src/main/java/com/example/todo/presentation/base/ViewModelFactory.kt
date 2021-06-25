package com.example.todo.presentation.base

import android.os.Bundle
import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.savedstate.SavedStateRegistryOwner

/**
 * Application MVVM ViewModel instances factory which supports passing [SavedStateHandle] instances
 * to created ViewModel instances.
 *
 * @param VM Concrete type of ViewModel
 */
class ViewModelFactory<VM : ViewModel>(
    savedStateRegistryOwner: SavedStateRegistryOwner,
    defaultArgs: Bundle?,
    private val viewModelFactory: (SavedStateHandle) -> VM
) : AbstractSavedStateViewModelFactory(savedStateRegistryOwner, defaultArgs) {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(
        key: String,
        modelClass: Class<T>,
        handle: SavedStateHandle
    ): T = viewModelFactory(handle) as T
}
package com.example.todo.presentation.base

import androidx.lifecycle.ViewModel

/**
 * Common MVVM ViewModel assisted factory for creation instances using Dagger 2 DI framework.
 *
 * @param T Concrete type of ViewModel
 */
interface ViewModelAssistedFactory<T : ViewModel>
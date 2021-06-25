package com.example.todo.presentation.base

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel

abstract class BaseViewModel(
    protected val savedStateHandle: SavedStateHandle
) : ViewModel()
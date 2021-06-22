package com.example.todo.presentation.fragment.example

import androidx.lifecycle.SavedStateHandle
import com.example.todo.domain.DeleteToDoInteractor
import com.example.todo.presentation.base.BaseViewModel
import com.example.todo.presentation.base.ViewModelAssistedFactory
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject

class ExampleViewModel @AssistedInject constructor(
    @Assisted savedStateHandle: SavedStateHandle,
    private val deleteToDoInteractor: DeleteToDoInteractor
): BaseViewModel(savedStateHandle) {
    @AssistedFactory
    interface Factory : ViewModelAssistedFactory<ExampleViewModel> {
        fun create(
            savedStateHandle: SavedStateHandle,
            someData: String
        ): ExampleViewModel
    }
}
package com.example.todo.presentation.fragment.todolist

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.todo.databinding.FragmentToDoControlsBinding
import com.example.todo.presentation.base.BaseBottomSheetFragment
import com.example.todo.presentation.fragment.example.ExampleFragment
import kotlin.reflect.KClass

private const val NO_LISTENER_EXCEPTION_MESSAGE = " must implement ToDoControlsFragmentListener"
private const val ID_ARGUMENT_KEY = "ID_ARGUMENT_KEY"

class ToDoControlsFragment :
    BaseBottomSheetFragment<ToDoControlsViewModel, ToDoControlsViewModel.Factory, FragmentToDoControlsBinding>() {
    lateinit var controlsFragmentListener: ToDoControlsFragmentListener

    interface ToDoControlsFragmentListener {
        fun onEditButtonClick(id: Long)
        fun onDeleteButtonClick(id: Long)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        try {
            controlsFragmentListener = context as ToDoControlsFragmentListener
        } catch (e: ClassCastException) {
            throw ClassCastException(
                activity.toString() + NO_LISTENER_EXCEPTION_MESSAGE
            )
        }
    }

    companion object {
        fun getInstance(id: Long) = ExampleFragment().apply {
            arguments?.putLong(ID_ARGUMENT_KEY, id)
        }
    }

    override val viewModelClass: KClass<ToDoControlsViewModel> = ToDoControlsViewModel::class

    override fun viewModelFactory(): (SavedStateHandle) -> ViewModel = { savedStateHandle ->
        viewModelAssistedFactory.create(
            savedStateHandle,
            requireArguments().getLong(ID_ARGUMENT_KEY)
        )
    }

    override fun createViewBinding(
        inflater: LayoutInflater,
        parent: ViewGroup?
    ): FragmentToDoControlsBinding = FragmentToDoControlsBinding.inflate(inflater, parent, false)
}
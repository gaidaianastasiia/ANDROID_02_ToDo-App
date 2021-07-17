package com.example.todo.presentation.fragment.todolist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.todo.databinding.FragmentToDoControlsBinding
import com.example.todo.entity.ToDoText
import com.example.todo.presentation.base.BaseBottomSheetFragment
import kotlin.reflect.KClass

private const val ID_ARGUMENT_KEY = "ID_ARGUMENT_KEY"
private const val TEXT_ARGUMENT_KEY = "TEXT_ARGUMENT_KEY"
private const val EDIT_TODO_FRAGMENT_DIALOG_TAG = "CREATE_TODO_FRAGMENT_DIALOG_TAG"
const val DELETE_TO_DO_REQUEST_KEY = "DELETE_TO_DO_REQUEST_KEY"
const val DELETED_TO_DO_ID = "DELETED_TO_DO_ID"

class ToDoControlsFragment :
    BaseBottomSheetFragment<
            ToDoControlsViewModel,
            ToDoControlsViewModel.Factory,
            FragmentToDoControlsBinding
            >() {
    override val viewModelClass: KClass<ToDoControlsViewModel> = ToDoControlsViewModel::class

    override fun createViewBinding(
        inflater: LayoutInflater,
        parent: ViewGroup?
    ) = FragmentToDoControlsBinding.inflate(inflater, parent, false)

    override fun viewModelFactory(): (SavedStateHandle) -> ViewModel = { savedStateHandle ->
        viewModelAssistedFactory.create(
            savedStateHandle,
            getToDoId(),
            getToDoText()
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setObserve()
        setUpViews()
    }

    private fun setObserve() {
        viewModel.showEditToDoDialog.observe(viewLifecycleOwner) { toDoText ->
            showEditToDoDialog(toDoText)
        }

        viewModel.requestDeleteToDo.observe(viewLifecycleOwner) { id ->
            requestDeleteTodo(id)
        }
    }

    private fun setUpViews() {
        setEditButtonClickListener()
        setDeleteButtonClickListener()
    }

    private fun setEditButtonClickListener() {
        binding.editToDoTextButton.setOnClickListener {
            viewModel.onEditButtonClick()
            dismissAllowingStateLoss()
        }
    }

    private fun setDeleteButtonClickListener() {
        binding.deleteToDoButton.setOnClickListener {
            viewModel.onDeleteButtonClick()
            dismissAllowingStateLoss()
        }
    }

    private fun showEditToDoDialog(toDoText: ToDoText) {
        val editToDoDialog = ToDoDialogFragment.getInstance(toDoText.id, toDoText.text)
        editToDoDialog.show(parentFragmentManager, EDIT_TODO_FRAGMENT_DIALOG_TAG)
    }

    private fun requestDeleteTodo(id: Long) {
        setFragmentResult(DELETE_TO_DO_REQUEST_KEY, bundleOf(DELETED_TO_DO_ID to id))
    }

    private fun getToDoId() = arguments?.getLong(ID_ARGUMENT_KEY)
        ?: throw IllegalStateException("To Do Id is not initialized")

    private fun getToDoText() = arguments?.getString(TEXT_ARGUMENT_KEY)
        ?: throw IllegalStateException("To Do Text is not initialized")

    companion object {
        fun getInstance(id: Long, text: String) = ToDoControlsFragment().apply {
            arguments = bundleOf(
                ID_ARGUMENT_KEY to id,
                TEXT_ARGUMENT_KEY to text
            )
        }
    }
}
package com.example.todo.presentation.fragment.todolist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDialog
import androidx.core.os.bundleOf
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.setFragmentResult
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.todo.databinding.FragmentToDoDialogBinding
import com.example.todo.presentation.base.BaseDialogFragment
import kotlin.reflect.KClass

private const val ID_ARGUMENT_KEY = "ID_ARGUMENT_KEY"
private const val TEXT_ARGUMENT_KEY = "TEXT_ARGUMENT_KEY"
const val EDIT_TO_DO_DIALOG_RESULT_KEY = "EDIT_TO_DO_DIALOG_RESULT_KEY"

class ToDoDialogFragment :
    BaseDialogFragment<
            ToDoDialogViewModel,
            ToDoDialogViewModel.Factory,
            FragmentToDoDialogBinding
            >() {
    override val viewModelClass: KClass<ToDoDialogViewModel> = ToDoDialogViewModel::class

    override fun createViewBinding(
        inflater: LayoutInflater,
        parent: ViewGroup?
    ): FragmentToDoDialogBinding = FragmentToDoDialogBinding.inflate(inflater, parent, false)

    override fun viewModelFactory(): (SavedStateHandle) -> ViewModel = { savedStateHandle ->
        viewModelAssistedFactory.create(
            savedStateHandle,
            arguments?.getLong(ID_ARGUMENT_KEY),
            arguments?.getString(TEXT_ARGUMENT_KEY)
        )
    }

    override fun onCreateDialog(savedInstanceState: Bundle?) = AppCompatDialog(requireContext())

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        isCancelable = true
        setObserve()
        setClickListeners()
    }

    private fun setObserve() {
        viewModel.dialogTitle.observe(viewLifecycleOwner) { dialogTitle ->
            setTitle(dialogTitle)
        }

        viewModel.positiveButtonText.observe(viewLifecycleOwner) { positiveButtonText ->
            setPositiveButtonText(positiveButtonText)
        }

        viewModel.enteredText.observe(viewLifecycleOwner) { text ->
            if (binding.toDoEditText.text.toString() != text) {
                binding.toDoEditText.setText(text)
            }
        }

        viewModel.storageSuccessResponse.observe(viewLifecycleOwner) {
            setFragmentResult(EDIT_TO_DO_DIALOG_RESULT_KEY, bundleOf())
        }

        viewModel.storageErrorResponse.observe(viewLifecycleOwner) {
            showErrorMessage()
        }
    }

    private fun setClickListeners() {
        binding.toDoEditText.doAfterTextChanged { text ->
            viewModel.saveEnteredText(text.toString())
        }

        binding.positiveButton.setOnClickListener {
            viewModel.onToDoDialogPositiveClick()
            dismiss()
        }

        binding.negativeButton.setOnClickListener {
            dismiss()
        }
    }

    private fun setTitle(dialogTitle: Int) {
        binding.toDoDialogTitleTextView.text = getString(dialogTitle)
    }

    private fun setPositiveButtonText(positiveButtonText: Int) {
        binding.positiveButton.text = getString(positiveButtonText)
    }

    companion object {
        fun getInstance(id: Long?, currentText: String) = ToDoDialogFragment().apply {
            arguments = bundleOf(
                Pair(ID_ARGUMENT_KEY, id),
                Pair(TEXT_ARGUMENT_KEY, currentText)
            )
        }
    }
}
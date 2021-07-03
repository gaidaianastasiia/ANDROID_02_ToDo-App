package com.example.todo.presentation.fragment.todolist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDialog
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import com.example.todo.R
import com.example.todo.databinding.FragmentToDoDialogBinding
import com.example.todo.presentation.base.BaseDialogFragment
import kotlin.reflect.KClass

private const val ID_ARGUMENT_KEY = "ID_ARGUMENT_KEY"
private const val TEXT_ARGUMENT_KEY = "TEXT_ARGUMENT_KEY"
const val TO_DO_DIALOG_RESULT_KEY = "TO_DO_DIALOG_RESULT_KEY"

class ToDoDialogFragment :
    BaseDialogFragment<ToDoDialogViewModel, ToDoDialogViewModel.Factory, FragmentToDoDialogBinding>() {
    override fun onCreateDialog(savedInstanceState: Bundle?) = AppCompatDialog(requireContext())

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (isEditableState()) {
            setEditableState()
        } else {
            setCreatableState()
        }

        isCancelable = true

        binding.positiveButton.setOnClickListener {
            val toDoText = binding.toDoEditText.text.toString()
            viewModel.onToDoDialogPositiveClick(toDoText)
            dismiss()
        }

        binding.negativeButton.setOnClickListener {
            dismiss()
        }

        viewModel.text.observe(viewLifecycleOwner) { text ->
            if (isEditableState()) {
                editToDo(text)
            } else {
                createToDo(text)
            }
        }

        viewModel.storagePositiveResponse.observe(viewLifecycleOwner) {
            setFragmentResult(TO_DO_DIALOG_RESULT_KEY, bundleOf())
        }
    }

    private fun isEditableState() = getToDoId() != null

    private fun getToDoId() = arguments?.getLong(ID_ARGUMENT_KEY)

    private fun getToDoText() = arguments?.getString(TEXT_ARGUMENT_KEY) ?: ""

    private fun setCreatableState() {
        val title = getString(R.string.create_to_do_dialog_title)
        val positiveButtonText = getString(R.string.create_to_do_dialog_positive_button)

        setTitle(title)
        setPositiveButtonText(positiveButtonText)
    }

    private fun setEditableState() {
        val title = getString(R.string.edit_to_do_dialog_title)
        val currentToDoText = getToDoText()
        val positiveButtonText = getString(R.string.edit_to_do_dialog_positive_button)

        setTitle(title)
        setCurrentToDoText(currentToDoText)
        setPositiveButtonText(positiveButtonText)
    }

    private fun setTitle(title: String) {
        binding.toDoDialogTitleTextView.text = title
    }

    private fun setCurrentToDoText(currentText: String) {
        binding.toDoEditText.run {
            setText(currentText)
        }
    }

    private fun setPositiveButtonText(positiveButtonText: String) {
        binding.positiveButton.text = positiveButtonText
    }

    private fun createToDo(text: String) {
        viewModel.createToDo(text)
    }

    private fun editToDo(text: String) {
        getToDoId()?.let { id ->
            viewModel.editToDo(id, text)
        }
    }

    companion object {
        fun getInstance(id: Long, text: String) = ToDoDialogFragment().apply {
            arguments = bundleOf(
                Pair(ID_ARGUMENT_KEY, id),
                Pair(TEXT_ARGUMENT_KEY, text)
            )
        }
    }

    override val viewModelClass: KClass<ToDoDialogViewModel> = ToDoDialogViewModel::class

    override fun createViewBinding(
        inflater: LayoutInflater,
        parent: ViewGroup?
    ): FragmentToDoDialogBinding = FragmentToDoDialogBinding.inflate(inflater, parent, false)

}
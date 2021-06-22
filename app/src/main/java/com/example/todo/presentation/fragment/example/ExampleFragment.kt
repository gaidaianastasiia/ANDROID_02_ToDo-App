package com.example.todo.presentation.fragment.example

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.todo.databinding.FragmentExampleBinding
import com.example.todo.presentation.base.BaseFragment
import kotlin.reflect.KClass

private const val ARGUMENT_KEY = "ARGUMENT_KEY"

class ExampleFragment: BaseFragment<ExampleViewModel, ExampleViewModel.Factory, FragmentExampleBinding>() {
    companion object {
        fun getInstance(someData: String) = ExampleFragment().apply {
            arguments?.putString(ARGUMENT_KEY, someData)
        }
    }

    override val viewModelClass: KClass<ExampleViewModel> = ExampleViewModel::class

    override fun viewModelFactory(): (SavedStateHandle) -> ViewModel = { savedStateHandle ->
        viewModelAssistedFactory.create(
            savedStateHandle,
            requireArguments().getString(ARGUMENT_KEY, "defaultText")
        )
    }

    override fun createViewBinding(
        inflater: LayoutInflater,
        parent: ViewGroup?
    ): FragmentExampleBinding = FragmentExampleBinding.inflate(inflater, parent, false)
}
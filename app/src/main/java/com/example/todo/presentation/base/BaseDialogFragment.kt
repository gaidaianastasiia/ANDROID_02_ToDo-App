package com.example.todo.presentation.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding
import dagger.android.support.DaggerDialogFragment
import javax.inject.Inject
import kotlin.reflect.KClass

abstract class BaseDialogFragment<VM: BaseViewModel, VMAF: ViewModelAssistedFactory<VM>, VB: ViewBinding> : DaggerDialogFragment() {
    @Inject
    protected lateinit var viewModelAssistedFactory: VMAF

    protected abstract val viewModelClass: KClass<VM>

    private var viewBinding: VB? = null
    val binding: VB
        get() = viewBinding ?: throw IllegalStateException("View binding is not initialized")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = createViewBinding(inflater, container).also { viewBinding = it }.root

    protected abstract fun createViewBinding(
        inflater: LayoutInflater,
        parent: ViewGroup?
    ): VB

    protected val viewModel: VM by lazy {
        ViewModelProvider(
            this,
            ViewModelFactory(this, null, viewModelFactory())
        ).get(viewModelClass.java)
    }

    protected open fun viewModelFactory(): (SavedStateHandle) -> ViewModel = { savedStateHandle ->
        viewModelAssistedFactory.let {  assistedFactory ->
            if (assistedFactory is BaseViewModelAssistedFactory<*>) {
                assistedFactory.create(savedStateHandle)
            } else {
                throw IllegalStateException(
                    "viewModelFactory() should be overridden as viewModelAssistedFactory is not BaseViewModelAssistedFactory"
                )
            }
        }
    }
}
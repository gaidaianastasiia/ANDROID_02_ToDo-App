package com.example.todo.presentation.base

import android.os.Bundle
import android.os.PersistableBundle
import android.view.LayoutInflater
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject
import kotlin.reflect.KClass

abstract class BaseActivity<
        VM: BaseViewModel,
        VMAF: ViewModelAssistedFactory<VM>,
        VB: ViewBinding
>: DaggerAppCompatActivity() {
    @Inject
    protected lateinit var viewModelAssistedFactory: VMAF

    protected abstract val viewModelClass: KClass<VM>

    private var viewBinding: VB? = null

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        viewBinding = createViewBinding(LayoutInflater.from(this)).also {
            setContentView(it.root)
        }
    }

    protected abstract fun createViewBinding(inflater: LayoutInflater): VB

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
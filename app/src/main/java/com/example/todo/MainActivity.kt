package com.example.todo.presentation.activity

import android.view.LayoutInflater
import com.example.todo.databinding.ActivityMainBinding
import com.example.todo.presentation.base.BaseActivity
import kotlin.reflect.KClass

class MainActivity : BaseActivity<MainViewModel, MainViewModel.Factory, ActivityMainBinding>() {
    override val viewModelClass: KClass<MainViewModel> = MainViewModel::class

    override fun createViewBinding(inflater: LayoutInflater): ActivityMainBinding =
        ActivityMainBinding.inflate(inflater)
}
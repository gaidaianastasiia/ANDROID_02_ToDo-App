package com.example.todo.presentation.activity

import android.os.Bundle

import android.view.LayoutInflater
import androidx.fragment.app.commit
import androidx.fragment.app.add
import com.example.todo.R
import com.example.todo.databinding.ActivityMainBinding
import com.example.todo.presentation.base.BaseActivity
import com.example.todo.presentation.fragment.todolist.ToDoListFragment
import kotlin.reflect.KClass

class MainActivity : BaseActivity<MainViewModel, MainViewModel.Factory, ActivityMainBinding>() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (savedInstanceState == null) {
            viewModel.onCreate()
        }

        viewModel.startEvent.observe(this) {
            startToDoFragment()
        }
    }

    override val viewModelClass: KClass<MainViewModel> = MainViewModel::class

    override fun createViewBinding(inflater: LayoutInflater): ActivityMainBinding =
        ActivityMainBinding.inflate(inflater)

    private fun startToDoFragment() {
        supportFragmentManager.commit {
            setReorderingAllowed(true)
            add<ToDoListFragment>(R.id.fragmentContainer)
        }
    }
}
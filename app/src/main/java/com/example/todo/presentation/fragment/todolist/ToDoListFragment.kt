package com.example.todo.presentation.fragment.todolist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.todo.databinding.FragmentToDoListBinding
import com.example.todo.presentation.base.BaseFragment
import kotlin.reflect.KClass

class ToDoListFragment :
    BaseFragment<ToDoListViewModel, ToDoListViewModel.Factory, FragmentToDoListBinding>(),
    ToDoAdapter.ToDoAdapterListener {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView = viewBinding?.listRecyclerView
        val adapter = ToDoAdapter(this)
        viewModel.list.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }
        recyclerView?.adapter = adapter
    }

    override fun onToDoClick(id: Long, text: String) {
        TODO("Not yet implemented")
    }

    override fun onToDoLongClick(id: Long) {
        TODO("Not yet implemented")
    }

    override val viewModelClass: KClass<ToDoListViewModel> = ToDoListViewModel::class

    override fun createViewBinding(
        inflater: LayoutInflater,
        parent: ViewGroup?
    ): FragmentToDoListBinding = FragmentToDoListBinding.inflate(inflater, parent, false)
}
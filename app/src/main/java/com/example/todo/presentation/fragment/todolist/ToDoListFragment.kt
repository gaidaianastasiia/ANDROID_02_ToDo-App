package com.example.todo.presentation.fragment.todolist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.todo.databinding.FragmentToDoListBinding
import com.example.todo.presentation.base.BaseFragment
import kotlin.reflect.KClass

private const val SHOPPING_LIST_CONTROLS_FRAGMENT_TAG = "ShoppingListsControlsFragment"

class ToDoListFragment :
    BaseFragment<ToDoListViewModel, ToDoListViewModel.Factory, FragmentToDoListBinding>(),
    ToDoAdapter.ToDoAdapterListener {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView = viewBinding?.listRecyclerView
        val adapter = ToDoAdapter(this)
        recyclerView?.adapter = adapter

        viewModel.list.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }

        viewModel.controls.observe(viewLifecycleOwner) {
            showControlsListDialog(it)
        }

        viewModel.onCreate()
    }

    override fun onToDoClick(id: Long, text: String) {
        TODO("Not yet implemented")
    }

    override fun onToDoLongClick(id: Long) {
        viewModel.onControlsListButtonClick(id)
    }

    private fun showControlsListDialog(id: Long) {
        val controlsListDialog = ToDoControlsFragment.getInstance(id)
        controlsListDialog.show(parentFragmentManager, SHOPPING_LIST_CONTROLS_FRAGMENT_TAG)
    }

    override val viewModelClass: KClass<ToDoListViewModel> = ToDoListViewModel::class

    override fun createViewBinding(
        inflater: LayoutInflater,
        parent: ViewGroup?
    ): FragmentToDoListBinding = FragmentToDoListBinding.inflate(inflater, parent, false)
}
package com.example.todo.presentation.fragment.todolist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.setFragmentResultListener
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.todo.databinding.FragmentToDoListBinding
import com.example.todo.presentation.base.BaseFragment
import kotlin.reflect.KClass

private const val CREATE_TODO_FRAGMENT_DIALOG_TAG = "CREATE_TODO_FRAGMENT_DIALOG_TAG"
private const val TO_DO_CONTROLS_FRAGMENT_TAG = "TO_DO_CONTROLS_FRAGMENT_TAG"

class ToDoListFragment :
    BaseFragment<ToDoListViewModel, ToDoListViewModel.Factory, FragmentToDoListBinding>(),
    ToDoAdapter.ToDoAdapterListener {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView = binding.listRecyclerView
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        val adapter = ToDoAdapter(this)

        viewModel.list.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }

        recyclerView.adapter = adapter

        viewModel.showCreateToDoDialog.observe(viewLifecycleOwner) {
            showCreateToDoDialog()
        }

        viewModel.controls.observe(viewLifecycleOwner) {
            showControlsListDialog(it.id, it.text)
        }

        setFragmentResultListener(TO_DO_DIALOG_RESULT_KEY) {_, _ ->
            viewModel.fetchList()
        }

        viewModel.fetchList()

        binding.addNewToDoFloatingActionButton.setOnClickListener {
            viewModel.onAddNewToDoButtonClick()
        }
    }

    override fun onToDoClick(id: Long) {
        TODO("Not yet implemented")
    }

    override fun onToDoLongClick(id: Long, text: String) {
        viewModel.onControlsListButtonClick(id)
    }

    private fun showCreateToDoDialog() {
        val createToDoDialog = ToDoDialogFragment()
        createToDoDialog.show(parentFragmentManager, CREATE_TODO_FRAGMENT_DIALOG_TAG)
    }

    private fun showControlsListDialog(id: Long, text: String) {
        val controlsToDoDialog = ToDoControlsFragment.getInstance(id, text)
        controlsToDoDialog.show(parentFragmentManager, TO_DO_CONTROLS_FRAGMENT_TAG)
    }

    override val viewModelClass: KClass<ToDoListViewModel> = ToDoListViewModel::class

    override fun createViewBinding(
        inflater: LayoutInflater,
        parent: ViewGroup?
    ): FragmentToDoListBinding = FragmentToDoListBinding.inflate(inflater, parent, false)
}
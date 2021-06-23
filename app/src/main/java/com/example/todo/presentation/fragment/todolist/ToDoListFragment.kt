package com.example.todo.presentation.fragment.todolist

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.todo.databinding.FragmentToDoListBinding

class ToDoListFragment : ToDoAdapter.ToDoAdapterListener, Fragment() {
    private var _binding: FragmentToDoListBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentToDoListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onToDoClick(id: Long, text: String) {
        TODO("Not yet implemented")
    }

    override fun onToDoLongClick(id: Long) {
        TODO("Not yet implemented")
    }
}
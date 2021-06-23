package com.example.todo.presentation.fragment.todolist

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.todo.databinding.FragmentToDoControlsBinding


class ToDoControlsFragment : Fragment() {
    private lateinit var binding: FragmentToDoControlsBinding
    lateinit var controlsFragmentListener: ToDoControlsFragmentListener

    interface ToDoControlsFragmentListener {
        fun onEditButtonClick(id: Long)
        fun onDeleteButtonClick(id: Long)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        try {
            controlsFragmentListener = context as ToDoControlsFragmentListener
        } catch (e: ClassCastException) {
            throw ClassCastException(
                activity.toString() + NO_LISTENER_EXCEPTION_MESSAGE
            )
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentToDoControlsBinding.inflate(inflater)
        return binding.root
    }

    companion object {
        private const val NO_LISTENER_EXCEPTION_MESSAGE =
            " must implement ToDoControlsFragmentListener"
    }
}
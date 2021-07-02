package com.example.todo.presentation.fragment.todolist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.todo.databinding.ToDoListItemLayoutBinding
import com.example.todo.entity.ToDo

private val DIFF_CALLBACK: DiffUtil.ItemCallback<ToDo> = object : DiffUtil.ItemCallback<ToDo>() {
    override fun areItemsTheSame(oldItem: ToDo, newItem: ToDo): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: ToDo, newItem: ToDo): Boolean {
        return oldItem == newItem
    }
}

class ToDoAdapter(
    private val toDoAdapterListener: ToDoAdapterListener
) : ListAdapter<ToDo, ToDoAdapter.ViewHolder>(DIFF_CALLBACK) {
    interface ToDoAdapterListener {
        fun onToDoClick(id: Long, text: String)
        fun onToDoLongClick(id: Long)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemBinding = ToDoListItemLayoutBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val id = getItem(position).id
        val text = getItem(position).text
        holder.bind(text)

        holder.view.setOnClickListener {
            toDoAdapterListener.onToDoClick(id, text)
        }

        holder.view.setOnLongClickListener {
            toDoAdapterListener.onToDoLongClick(id)
            true
        }
    }

    class ViewHolder(
        private val itemBinding: ToDoListItemLayoutBinding
    ) : RecyclerView.ViewHolder(itemBinding.root) {
        lateinit var view: View

        fun bind(toDoText: String) {
            itemBinding.itemTextTextView.text = toDoText
            view = itemBinding.root
        }
    }
}
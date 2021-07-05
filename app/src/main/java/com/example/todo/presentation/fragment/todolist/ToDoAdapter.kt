package com.example.todo.presentation.fragment.todolist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.todo.R
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
        fun onToDoClick(id: Long, doneStatus: Boolean)
        fun onToDoLongClick(id: Long, text: String)
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
        val doneStatus = getItem(position).doneStatus
        holder.bind(text, doneStatus)

        holder.view.setOnClickListener {
            toDoAdapterListener.onToDoClick(id, doneStatus)
        }

        holder.view.setOnLongClickListener {
            toDoAdapterListener.onToDoLongClick(id, text)
            true
        }
    }

    class ViewHolder(
        private val itemBinding: ToDoListItemLayoutBinding
    ) : RecyclerView.ViewHolder(itemBinding.root) {
        lateinit var view: View

        fun bind(toDoText: String, doneStatus: Boolean) {
            itemBinding.itemTextTextView.text = toDoText

            if(doneStatus) {
                itemBinding.itemTextTextView.setCheckMarkDrawable(R.drawable.ic_baseline_check_box_24)
            } else {
                itemBinding.itemTextTextView.setCheckMarkDrawable(R.drawable.ic_baseline_check_box_outline_blank_24)
            }

            itemBinding.itemTextTextView.isChecked = doneStatus

            view = itemBinding.root
        }
    }
}
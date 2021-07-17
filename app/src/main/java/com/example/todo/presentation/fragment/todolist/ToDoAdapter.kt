package com.example.todo.presentation.fragment.todolist

import android.view.LayoutInflater
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
    private val onItemClickListener: OnItemClickListener
) : ListAdapter<ToDo, ToDoAdapter.ViewHolder>(DIFF_CALLBACK) {
    interface OnItemClickListener {
        fun onItemClick(id: Long, doneStatus: Boolean)
        fun onItemLongClick(id: Long, text: String)
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
        val toDo = getItem(position)
        val id = toDo.id
        val text = toDo.text
        val doneStatus = toDo.doneStatus

        holder.bind(toDo)

        holder.itemBinding.root.setOnClickListener {
            onItemClickListener.onItemClick(id, doneStatus)
        }

        holder.itemBinding.root.setOnLongClickListener {
            onItemClickListener.onItemLongClick(id, text)
            true
        }

        holder.itemBinding.itemDoneStatusCheckBox.setOnClickListener {
            onItemClickListener.onItemClick(id, doneStatus)
        }
    }

    class ViewHolder(
        val itemBinding: ToDoListItemLayoutBinding
    ) : RecyclerView.ViewHolder(itemBinding.root) {

        fun bind(toDo: ToDo) {
            itemBinding.itemTextTextView.text = toDo.text
            itemBinding.itemDoneStatusCheckBox.isChecked = toDo.doneStatus
        }
    }
}
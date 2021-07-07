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

    holder.itemBinding.root.setOnClickListener {
      toDoAdapterListener.onToDoClick(id, doneStatus)
    }

    holder.itemBinding.root.setOnLongClickListener {
      toDoAdapterListener.onToDoLongClick(id, text)
      true
    }

    holder.itemBinding.itemDoneStatusCheckBox.setOnClickListener {
      toDoAdapterListener.onToDoClick(id, doneStatus)
    }
  }

  class ViewHolder(
    val itemBinding: ToDoListItemLayoutBinding
  ) : RecyclerView.ViewHolder(itemBinding.root) {

    fun bind(toDoText: String, doneStatus: Boolean) {
      itemBinding.itemTextTextView.text = toDoText
      itemBinding.itemDoneStatusCheckBox.isChecked = doneStatus
    }
  }
}
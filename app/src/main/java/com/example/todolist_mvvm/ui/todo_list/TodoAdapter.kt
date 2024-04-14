package com.example.myapplication

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.todolist_mvvm.R
import com.example.todolist_mvvm.ui.todo_list.TodoInterface
import java.text.SimpleDateFormat
import java.util.*

class TodoAdapter(var list : List<Todo>, val listener : TodoInterface) : RecyclerView.Adapter<TodoAdapter.TodoViewHolder>() {

    inner class TodoViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        var btnCheck = itemView.findViewById<CheckBox>(R.id.btnCheck)
        val txtDesc = itemView.findViewById<TextView>(R.id.txtDesc)
        val txtTitle = itemView.findViewById<TextView>(R.id.title)
        val time = itemView.findViewById<TextView>(R.id.time)
        val date = itemView.findViewById<TextView>(R.id.date)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.todo_item, parent, false)

        return TodoViewHolder(view)
    }

    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
        holder.itemView.apply {
            holder.btnCheck.isChecked = list[position].isChecked
            holder.txtDesc.text = list[position].desc
            holder.txtTitle.text = list[position].title

            // xử lí thời gian
            val calendar = Calendar.getInstance()
            calendar.timeInMillis = list[position].timeStamp

            val hour = calendar.get(Calendar.HOUR_OF_DAY)
            val minute = calendar.get(Calendar.MINUTE)
            val dateFormat = SimpleDateFormat("dd/MM", Locale.getDefault())

            holder.time.text = String.format("%02d:%02d", hour, minute)
            holder.date.text = dateFormat.format(calendar.time)
        }


        holder.itemView.setOnLongClickListener {

            listener.deleteTaskListener(list[position])
            return@setOnLongClickListener true
        }

        holder.itemView.setOnClickListener {
            listener.updateTaskListener(list[position])
        }

        holder.btnCheck.setOnClickListener {
            listener.isCheckListener(list[position])
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }
}
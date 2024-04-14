package com.example.todolist_mvvm.ui.todo_list.fragment

import Helper
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.myapplication.Todo
import com.example.todolist_mvvm.R
import com.example.todolist_mvvm.ui.todo_list.TodoListEvent
import com.example.todolist_mvvm.ui.todo_list.TodoListViewModel
import com.example.todolist_mvvm.util.TimePickerHandler
import java.text.SimpleDateFormat
import java.util.Date

class todoUtils(
    private val viewModel: TodoListViewModel,
    private val context: Context
) {
    val helper = Helper()
    fun deleteTodo(todo: Todo) {
        AlertDialog.Builder(context)
            .setTitle("Delete Task")
            .setMessage("Do you to delete this task ?")
            .setPositiveButton("Yes") { _, _ ->
                viewModel.onEvent(TodoListEvent.DeleteTodo(todo))
                helper.cancelNotification(todo, todo.id?.toLong() ?: 0, context)            }
            .setNegativeButton("No", null)
            .show()
            .setCancelable(false)
    }

    fun updateTask(todoTask: Todo) {
        val dialogView = LayoutInflater.from(context).inflate(R.layout.update_dialog, null)
        val dialog = AlertDialog.Builder(context)
            .setView(dialogView)
            .setCancelable(false)
            .create()

        val edtUpdateTaskTitle = dialogView.findViewById<EditText>(R.id.edtUpdateTaskTitle)
        val edtUpdateTaskDesc = dialogView.findViewById<EditText>(R.id.edtUpdateTaskDesc)
        val edtUpdateTaskDate = dialogView.findViewById<EditText>(R.id.edtUpdateTaskDate)
        var savedTimeStamp: Long = todoTask.timeStamp
        val dateFormat = SimpleDateFormat("yy/MM/dd HH:mm")
        val date = Date(savedTimeStamp)
        edtUpdateTaskDate.setText(dateFormat.format(date))
        edtUpdateTaskTitle.setText(todoTask.title)
        edtUpdateTaskDesc.setText(todoTask.desc)

        edtUpdateTaskDate.setOnClickListener { _ ->
            val timeHandler = TimePickerHandler(context)
            // Chờ đến khi người dùng chọn xong cả ngày và giờ
            timeHandler.setOnDateTimeChangeListener { timeStamp ->
                savedTimeStamp = timeStamp
                val dateFormat = SimpleDateFormat("yy/MM/dd HH:mm")
                val date = Date(timeStamp)
                edtUpdateTaskDate.setText(dateFormat.format(date))
            }
            timeHandler.datePickerRun()
        }


        val btnYes = dialogView.findViewById<Button>(R.id.btnYes)
        val btnNo = dialogView.findViewById<Button>(R.id.btnNo)
        val oldStatus = todoTask.isChecked

        btnYes.setOnClickListener {
            val title = edtUpdateTaskTitle.text.toString().trim()
            val desc = edtUpdateTaskDesc.text.toString().trim()
            val date = savedTimeStamp
            if(desc.isNotEmpty() && title.isNotEmpty()) {
                val newTodo = Todo(todoTask.id, edtUpdateTaskTitle.text.toString().trim(), edtUpdateTaskDesc.text.toString().trim(), oldStatus, savedTimeStamp)
                viewModel.onEvent(TodoListEvent.OnChange(newTodo))
                dialog.dismiss()
            }
            else {
                Toast.makeText(context, "Hãy điền đầy đủ thông tin", Toast.LENGTH_SHORT).show()
            }
        }

        btnNo.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()
    }

    fun isCheck(todoTask: Todo) {
        Log.d("isCheck", "isCheck: ${todoTask.timeStamp}")
        val updateTask = Todo(todoTask.id, todoTask.title, todoTask.desc, !todoTask.isChecked, todoTask.timeStamp)
        viewModel.onEvent(TodoListEvent.OnDoneChange(updateTask))
    }
}
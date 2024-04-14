package com.example.todolist_mvvm.ui.todo_list

import com.example.myapplication.Todo

interface TodoInterface {

    fun deleteTaskListener(todoTask : Todo)
    fun updateTaskListener(todoTask : Todo)
    fun isCheckListener(todoTask: Todo)
}
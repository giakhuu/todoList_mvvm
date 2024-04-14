package com.example.todolist_mvvm.ui.todo_list

import com.example.myapplication.Todo

sealed class TodoListEvent {
    data class  DeleteTodo(val todo: Todo): TodoListEvent()
    data class OnChange(val todo: Todo): TodoListEvent()
    data class OnDoneChange(val todo: Todo): TodoListEvent()
    data class OnAdd(val todo: Todo): TodoListEvent()
}

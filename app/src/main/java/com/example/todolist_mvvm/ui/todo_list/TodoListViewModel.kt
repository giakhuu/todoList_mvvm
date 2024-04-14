package com.example.todolist_mvvm.ui.todo_list

import Helper
import android.content.Context
import android.util.Log
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import com.example.myapplication.Todo
import com.example.todolist_mvvm.data.TodoRespository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class TodoListViewModel
@Inject constructor(
    private val repository : TodoRespository,
    private val context: Context
): ViewModel()
{
    private val _todos = repository.getTodo()
    val todos: LiveData<List<Todo>> = _todos
    val checkedItemsLiveData: LiveData<List<Todo>> = _todos.map { items ->
        items.filter { it.isChecked }
    }

    // Biến đổi dữ liệu LiveData để trả về danh sách item với check là false
    val uncheckedItemsLiveData: LiveData<List<Todo>> = _todos.map { items ->
        items.filter { !it.isChecked }
    }
    private var deletedTodo : Todo? = null

    private val helper = Helper()

    fun onEvent(event: TodoListEvent) {
        when(event) {
            is TodoListEvent.OnAdd -> {
                viewModelScope.launch {
                    val todoId = repository.insertTodo(event.todo)
                    helper.scheduleNotification(event.todo, todoId, context)
                }
            }
            is TodoListEvent.OnChange -> {
                viewModelScope.launch {
                    repository.insertTodo(event.todo)
                    helper.scheduleNotification(event.todo, event.todo.id?.toLong() ?: 0, context)
                }
            }
            is TodoListEvent.OnDoneChange -> {
                viewModelScope.launch {
                    repository.insertTodo(event.todo)
                    if(event.todo.isChecked) {
                        helper.cancelNotification(event.todo, event.todo.id?.toLong() ?: 0, context)
                    }
                    else {
                        helper.scheduleNotification(event.todo, event.todo.id?.toLong()?:0, context)
                    }
                }
            }
            is TodoListEvent.DeleteTodo -> {
                viewModelScope.launch {
                    deletedTodo = event.todo
                    repository.deleteTodo(event.todo)
                    helper.cancelNotification(event.todo, event.todo.id?.toLong() ?: 0, context)
                }
            }
            else -> {
            }
        }
    }
}

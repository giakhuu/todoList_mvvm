package com.example.todolist_mvvm.data

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.myapplication.Todo

interface TodoRespository {
    @PrimaryKey(autoGenerate = true)
    suspend fun insertTodo (todo : Todo) : Long
    suspend fun getTodoById (id : Int) : LiveData<Todo?>
    suspend fun deleteTodo (todo : Todo)
    fun getTodo () : LiveData<List<Todo>>
}
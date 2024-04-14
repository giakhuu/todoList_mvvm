package com.example.todolist_mvvm.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.myapplication.Todo

@Database (
    entities = [Todo::class],
    version = 1
)
abstract class TodoDatabase : RoomDatabase() {
    abstract val dao : TodoDao
}
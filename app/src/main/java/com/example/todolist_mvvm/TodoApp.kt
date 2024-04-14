package com.example.todolist_mvvm

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class TodoApp @Inject constructor() : Application() {
    var instance = this

    override fun onCreate() {
        super.onCreate()
        instance = this
    }
}
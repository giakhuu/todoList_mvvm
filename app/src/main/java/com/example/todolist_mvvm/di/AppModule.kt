package com.example.todolist_mvvm.di

import TodoRepositoryImpl
import android.app.Application
import android.content.Context
import androidx.room.Room
import com.example.todolist_mvvm.TodoApp
import com.example.todolist_mvvm.data.TodoDatabase
import com.example.todolist_mvvm.data.TodoRespository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideTodoDatabase(app : Application) : TodoDatabase {
        return Room.databaseBuilder(
            app,
            TodoDatabase::class.java,
            "todo_db"
        ).build()
    }

    @Provides
    @Singleton
    fun provideTodoRepository(db: TodoDatabase): TodoRespository {
        return TodoRepositoryImpl(db.dao)
    }

    @Provides
    @Singleton
    fun provideApplicationContext(app: Application): Context {
        return app.applicationContext
    }

}
package com.example.todolist_mvvm.data
import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.myapplication.Todo


@Dao
interface TodoDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTodo(todo: Todo) : Long

    @Query("SELECT * from todo WHERE id = :id")
    fun getTodoById(id: Int): LiveData<Todo?>

    @Delete
    suspend fun deleteTodo (todo : Todo)

    @Query("SELECT * from todo")
    fun getTodo() : LiveData<List<Todo>>
}

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.example.myapplication.Todo
import com.example.todolist_mvvm.data.TodoDao
import com.example.todolist_mvvm.data.TodoRespository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

class TodoRepositoryImpl(private val dao: TodoDao) : TodoRespository {

    override suspend fun insertTodo(todo: Todo) : Long {
        return withContext(Dispatchers.IO) {
            dao.insertTodo(todo)
        }
    }

    override suspend fun getTodoById(id: Int): LiveData<Todo?> {
        return dao.getTodoById(id)
    }

    override suspend fun deleteTodo(todo: Todo) {
        withContext(Dispatchers.IO) {
            dao.deleteTodo(todo)
        }
    }

    override fun getTodo(): LiveData<List<Todo>> {
        return dao.getTodo()
    }
}

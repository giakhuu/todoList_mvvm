import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.core.content.ContextCompat
import com.example.myapplication.Todo
import com.example.todolist_mvvm.util.messageExtra
import com.example.todolist_mvvm.util.titleExtra

class Helper {
    fun scheduleNotification(todo: Todo, id: Long, context: Context) {
        if (todo.timeStamp <= System.currentTimeMillis()) {
            return
        }
        val intent = Intent(context, com.example.todolist_mvvm.util.Notification::class.java)
        intent.putExtra(titleExtra, todo.title)
        intent.putExtra(messageExtra, todo.desc)

        val pendingIntent = PendingIntent.getBroadcast(
            context,
            id.toInt(),
            intent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )

        val alarmManager = ContextCompat.getSystemService(
            context,
            AlarmManager::class.java
        ) as AlarmManager

        alarmManager.setExactAndAllowWhileIdle(
            AlarmManager.RTC_WAKEUP,
            todo.timeStamp,
            pendingIntent
        )
    }

    fun cancelNotification(todo: Todo, id: Long, context: Context) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, com.example.todolist_mvvm.util.Notification::class.java)
        val pendingIntent = PendingIntent.getBroadcast(
            context,
            id.toInt(),
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
        pendingIntent?.let {
            alarmManager.cancel(it)
            Log.d("del id", id.toString())
            it.cancel()
        }
    }
}



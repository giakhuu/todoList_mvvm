package com.example.todolist_mvvm.ui.todo_list


import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.myapplication.fragment.Complete
import com.example.myapplication.fragment.Incomplete
import com.example.todolist_mvvm.R
import com.example.todolist_mvvm.databinding.TodoListBinding
import com.example.todolist_mvvm.ui.todo_list.fragment.Add
import com.example.todolist_mvvm.ui.todo_list.fragment.Note
import com.example.todolist_mvvm.util.channelID
import dagger.hilt.android.AndroidEntryPoint
@AndroidEntryPoint
class TodoListActivity : AppCompatActivity() {
    private lateinit var binding : TodoListBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = TodoListBinding.inflate(layoutInflater)
        setContentView(binding.root)


        if (savedInstanceState == null) {
            // Sử dụng Incomplete Fragment với các LiveData đã khai báo
            replaceFragment(Incomplete())
        }


        binding.bottomNav.setOnItemSelectedListener {
            when(it.itemId) {
                R.id.incomplete -> {
                    replaceFragment(Incomplete())

                    return@setOnItemSelectedListener true
                }
                R.id.complete -> {
                    replaceFragment(Complete())
                    return@setOnItemSelectedListener true
                }
                R.id.add -> {
                    replaceFragment(Add())
                    return@setOnItemSelectedListener true
                }
                R.id.note -> {
                    replaceFragment(Note())
                    return@setOnItemSelectedListener true
                }
                else -> {
                    replaceFragment(Incomplete())

                    return@setOnItemSelectedListener false
                }
            }
        }


        createNotificationChannel()
    }

    private fun replaceFragment(fragment: Fragment) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.setCustomAnimations(
            R.anim.slide_in_right,  // Animation khi hiển thị fragment mới
            R.anim.slide_out_left  // Animation khi ẩn fragment cũ
        )
        fragmentTransaction.replace(R.id.fragment_container_view_tag, fragment)
        fragmentTransaction.commit()
    }

    private fun createNotificationChannel()
    {
        val name = "Notif Channel"
        val desc = "A Description of the Channel"
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val channel = NotificationChannel(channelID, name, importance)
        channel.description = desc
        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }

}

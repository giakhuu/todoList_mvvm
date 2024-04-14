package com.example.todolist_mvvm.ui.todo_list.fragment

import Helper
import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import android.widget.TimePicker
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.Todo
import com.example.todolist_mvvm.databinding.FragmentAddBinding
import com.example.todolist_mvvm.ui.todo_list.TodoListEvent
import com.example.todolist_mvvm.ui.todo_list.TodoListViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.util.Calendar
import androidx.lifecycle.viewModelScope

@AndroidEntryPoint
class Add : Fragment(), DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {
    private var _binding: FragmentAddBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel : TodoListViewModel
    private val helper = Helper()
    // biến cho cái ngày
    // thêm cái nhập ngày tháng
    var day = 0
    var month = 0
    var year = 0
    var hour = 0
    var minute = 0

    var savedday = 0
    var savedmonth = 0
    var savedyear = 0
    var savedhour = 0
    var savedminute = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAddBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(TodoListViewModel::class.java)

        // Kiểm tra xem requireContext() trả về null hay không
        val context = requireContext()

        // thêm
        binding.btnAdd.setOnClickListener {
            addTasks()
        }
        binding.datePickerContainer.setOnClickListener { view ->
            getDateTimeCalendar()
            DatePickerDialog(requireContext(), this, year, month, day).show()
        }
        binding.timePickerContainer.setOnClickListener { view ->
            getDateTimeCalendar()
            TimePickerDialog(requireContext(), this, hour, minute, true).show()
        }
    }


    @SuppressLint("NotifyDataSetChanged")
    private fun addTasks() {

        val title = binding.edtTasks.text.toString().trim()
        val desc = binding.edtTasksDesc.text.toString().trim()

        if (title.isNotEmpty()) {
            // chuyển thời gian
            val calendar = Calendar.getInstance()
            calendar.set(savedyear, savedmonth, savedday, savedhour, savedminute)
            val timestamp = calendar.timeInMillis


            val newTodo = Todo(null, title, desc, false, timestamp)
            viewModel.onEvent(TodoListEvent.OnAdd(newTodo))
            binding.edtTasks.setText("")
            binding.edtTasksDesc.setText("")
            binding.edtTasksDate.setText("")


            clearTimeVar()
        }
        else {
            Toast.makeText(requireContext(), "Tiêu đề là thông tin bắt buộc", Toast.LENGTH_SHORT).show()
        }
    }

    private fun clearTimeVar () {
        day = 0
        month = 0
        year = 0
        hour = 0
        minute = 0

        savedday = 0
        savedmonth = 0
        savedyear = 0
        savedhour = 0
        savedminute = 0
    }

    override fun onDateSet(p0: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        savedday = dayOfMonth
        savedmonth = month
        savedyear = year

        binding.edtTasksDate.setText(String.format("%02d/%02d/%02d", day, month, year))

    }

    override fun onTimeSet(p0: TimePicker?, hourOfDay: Int, minute: Int) {
        savedhour = hourOfDay
        savedminute = minute

        // Cập nhật giá trị của thời gian
        getDateTimeCalendar()
        binding.edtTasksTime.setText(String.format("%02d:%02d", savedhour, savedminute))
    }
    private fun getDateTimeCalendar () {
        val cal = Calendar.getInstance()
        day = cal.get(Calendar.DAY_OF_MONTH)
        month = cal.get(Calendar.MONTH)
        year = cal.get(Calendar.YEAR)
        hour = cal.get(Calendar.HOUR_OF_DAY)
        minute = cal.get(Calendar.MINUTE)
    }
}
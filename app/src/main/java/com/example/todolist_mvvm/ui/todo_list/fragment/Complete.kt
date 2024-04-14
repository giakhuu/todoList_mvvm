package com.example.myapplication.fragment


import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.*
import com.example.todolist_mvvm.databinding.FragmentCompleteBinding
import com.example.todolist_mvvm.ui.todo_list.TodoInterface
import com.example.todolist_mvvm.ui.todo_list.TodoListViewModel
import com.example.todolist_mvvm.ui.todo_list.fragment.todoUtils
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class Complete() : Fragment(), DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {

    private var _binding: FragmentCompleteBinding? = null
    private val binding get() = _binding!!

    private lateinit var todoAdapter : TodoAdapter
    private lateinit var todoUtils: todoUtils

    private lateinit var viewModel : TodoListViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCompleteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(TodoListViewModel::class.java)

        // Kiểm tra xem requireContext() trả về null hay không
        val context = requireContext()
        if (context != null) {
            todoUtils = todoUtils(viewModel, context)
            viewModel.checkedItemsLiveData.observe(viewLifecycleOwner) { todos ->
                if (todos.isEmpty()) {
                    // Nếu danh sách todos rỗng, ẩn RecyclerView và hiển thị layout khác
                    binding.rvComplete.visibility = View.GONE
                    binding.emptyLayout.visibility = View.VISIBLE // Đây là id của layout thay thế
                } else {
                    // Nếu danh sách todos không rỗng, hiển thị RecyclerView và ẩn layout khác
                    binding.rvComplete.visibility = View.VISIBLE
                    binding.emptyLayout.visibility = View.GONE
                    // Thiết lập adapter và layoutManager cho RecyclerView
                    todoAdapter = TodoAdapter(todos, object : TodoInterface {
                        override fun deleteTaskListener(todoTask: Todo) {
                            todoUtils.deleteTodo(todoTask)
                        }

                        override fun updateTaskListener(todoTask: Todo) {
                            todoUtils.updateTask(todoTask)
                        }

                        override fun isCheckListener(todoTask: Todo) {
                            todoUtils.isCheck(todoTask)
                        }
                    })
                    binding.rvComplete.adapter = todoAdapter
                    binding.rvComplete.layoutManager = LinearLayoutManager(context)
                }
            }
        }
    }

    // Override phương thức onDateSet để tránh lỗi
    override fun onDateSet(p0: DatePicker?, p1: Int, p2: Int, p3: Int) {
        // Code xử lý khi người dùng chọn ngày trong DatePickerDialog
    }

    override fun onTimeSet(p0: TimePicker?, hourOfDay: Int, minute: Int) {
        // Code xử lý khi người dùng chọn thời gian trong TimePickerDialog
    }
}

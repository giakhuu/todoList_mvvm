package com.example.todolist_mvvm.util

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.util.Log
import android.widget.DatePicker
import android.widget.TimePicker
import android.content.Context
import androidx.core.content.ContentProviderCompat.requireContext
import java.util.*

class TimePickerHandler(private val context: Context) : DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {
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

    var result : Long = 0
    private var onDateTimeChangeListener: ((Long) -> Unit)? = null

    fun getTime() : Long {
        // Không cần gọi datePickerRun() ở đây
        return result
    }

    fun datePickerRun() {
        getDateTimeCalendar()
        DatePickerDialog(context, this, year, month, day).show()
    }

    fun setOnDateTimeChangeListener(listener: (Long) -> Unit) {
        onDateTimeChangeListener = listener
    }

    override fun onDateSet(p0: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        savedday = dayOfMonth
        savedmonth = month
        savedyear = year
        // Gọi TimePickerDialog sau khi chọn ngày
        TimePickerDialog(context, this, hour, minute, true).show()
    }

    override fun onTimeSet(p0: TimePicker?, hourOfDay: Int, minute: Int) {
        savedhour = hourOfDay
        savedminute = minute

        // Cập nhật giá trị của thời gian
        getDateTimeCalendar()

        // Tính toán result và trả về giá trị
        val calendar = Calendar.getInstance()
        calendar.set(savedyear, savedmonth, savedday, savedhour, savedminute)
        result = calendar.timeInMillis

        onDateTimeChangeListener?.invoke(result)
    }

    private fun getDateTimeCalendar () {
        val cal = Calendar.getInstance()
        day = cal.get(Calendar.DAY_OF_MONTH)
        month = cal.get(Calendar.MONTH)
        year = cal.get(Calendar.YEAR)
        hour = cal.get(Calendar.HOUR)
        minute = cal.get(Calendar.MINUTE)
    }
}
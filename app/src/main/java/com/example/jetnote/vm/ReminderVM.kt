package com.example.jetnote.vm

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.icu.util.Calendar
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class ReminderVM: ViewModel(){

    private val calendar: Calendar = Calendar.getInstance()

    val getTimeReminder = mutableStateOf<Long?>(null)

    val getDateTimeReminder = mutableStateOf(Calendar.getInstance().time)

    private var date =
        Triple(
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )
    private fun datePicker (
        context: Context
    ) = DatePickerDialog (
        context,
        { _, y, m, d ->
            date = Triple(y, m, d)
        },
        date.first, // year
        date.second, // month
        date.third // day of month
    )
    private fun timePicker (
        context: Context
    ) = TimePickerDialog (
        context, { _, hh, mm ->
            calendar.set(
                date.first, // year
                date.second, // month
                date.third, // day of month
                hh, // hour
                mm, // minute
                0 // second
            )
            getTimeReminder.value = calendar.timeInMillis
            getDateTimeReminder.value = calendar.time
        },
        calendar.get(Calendar.HOUR),
        calendar.get(Calendar.MINUTE),
        false
    )


    val getTimePicker: (context:Context) -> Unit = { timePicker(it).show() }

    val getDatePicker: (context:Context) -> Unit = { datePicker(it).show() }

}

package com.calendar.test

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.calendar.base.CalendarSelectionType
import com.calendar.base.CalendarType
import com.calendar.base.CalendarView
import java.util.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val calendar = findViewById<CalendarView>(R.id.calendarView)

        calendar.initCalendar(
            CalendarType.Jalali,
            CalendarSelectionType.WITHOUT_SELECTION,
            LinearLayoutManager.VERTICAL
        )

        calendar.submitNextDates(Calendar.MONTH, 80)
    }
}
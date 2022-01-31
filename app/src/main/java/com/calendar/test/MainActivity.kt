package com.calendar.test

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.calendar.CalendarView
import com.calendar.base.CalendarRegional
import com.calendar.base.types.rangeslelection.RangeSelection
import java.util.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val calendar = findViewById<CalendarView>(R.id.calendarView)

        calendar.initCalendar(
            CalendarRegional.Jalali,
            RangeSelection(),
            LinearLayoutManager.HORIZONTAL
        )

        calendar.submitNextDates(Calendar.MONTH, 80)
    }
}
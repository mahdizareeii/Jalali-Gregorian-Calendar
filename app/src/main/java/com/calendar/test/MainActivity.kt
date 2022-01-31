package com.calendar.test

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.calendar.CalendarView
import com.calendar.base.calendar.MyJalaliCalendar
import com.calendar.base.calendar.RegionalType
import com.calendar.base.model.DayItem
import com.calendar.base.types.CalendarProperties
import com.calendar.base.types.singleselection.SingleSelection
import com.calendar.base.types.singleselection.SingleSelectionListener
import java.util.*

class MainActivity : AppCompatActivity(), SingleSelectionListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val calendar = findViewById<CalendarView>(R.id.calendarView)

        calendar.initCalendar(
            RegionalType.Jalali,
            SingleSelection(this),
            LinearLayoutManager.VERTICAL,
            CalendarProperties(
                today = MyJalaliCalendar().getToday()
            )
        )

        calendar.submitNextDates(Calendar.MONTH, 80)
    }

    override fun onDaySelected(dayItem: DayItem) {
        Toast.makeText(this, dayItem.toString(), Toast.LENGTH_SHORT).show()
    }
}
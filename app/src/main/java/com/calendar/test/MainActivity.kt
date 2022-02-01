package com.calendar.test

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.calendar.CalendarView
import com.calendar.base.calendar.MyGregorianCalendar
import com.calendar.base.calendar.RegionalType
import com.calendar.base.model.DayItem
import com.calendar.base.types.CalendarProperties
import com.calendar.base.types.rangeslelection.RangeSelection
import com.calendar.base.types.rangeslelection.RangeSelectionListener
import java.util.*

class MainActivity : AppCompatActivity(), RangeSelectionListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val calendar = findViewById<CalendarView>(R.id.calendarView)

        calendar.initCalendar(
            RegionalType.Gregorian,
            LinearLayoutManager.VERTICAL,
            CalendarProperties(
                today = MyGregorianCalendar().getToday(),
                calendarType = RangeSelection(this)
            )
        )

        calendar.submitNextDates(Calendar.MONTH, 80)
    }

    override fun onCheckInSelected(dayItem: DayItem) {
        Toast.makeText(this, dayItem.toString(), Toast.LENGTH_SHORT).show()

    }

    override fun onCheckOutSelected(dayItem: DayItem) {
        Toast.makeText(this, dayItem.toString(), Toast.LENGTH_SHORT).show()
    }
}
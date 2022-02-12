package com.calendar.test

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.calendar.CalendarView
import com.calendar.base.calendar.RegionalType
import com.calendar.base.model.DayItem
import com.calendar.base.types.CalendarProperties
import com.calendar.base.types.CalendarProperties.Companion.VERTICAL
import com.calendar.base.types.rangeslelection.RangeSelection
import com.calendar.base.types.rangeslelection.RangeSelectionListener
import java.util.*

class MainActivity : AppCompatActivity(), RangeSelectionListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val calendar = findViewById<CalendarView>(R.id.calendarView)

        calendar.initCalendar(
            CalendarProperties(
                regionalType = RegionalType.Jalali,
                calendarType = RangeSelection(this),
                availableFromToday = true,
                calendarOrientation = VERTICAL,
                showDaysPrice = true,
                customDays = arrayListOf(
                    DayItem(1400, 11, 26, 550000.0, false, false),
                    DayItem(1400, 11, 27, 12300.0, true, false),
                    DayItem(1400, 11, 28, 430000.0, true, true),
                    DayItem(1400, 12, 5, 78893.0, false, false),
                    DayItem(1400, 12, 6, 99999.0, false, false),
                    DayItem(1400, 12, 7, 100000.0, false, false),
                    DayItem(1400, 12, 8, 100000.0, false, true),
                )
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
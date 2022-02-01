package com.calendar.test

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.calendar.CalendarView
import com.calendar.base.calendar.RegionalType
import com.calendar.base.model.DayItem
import com.calendar.base.types.CalendarProperties
import com.calendar.base.types.CalendarProperties.Companion.HORIZONTAL
import com.calendar.base.types.CalendarProperties.Companion.VERTICAL
import com.calendar.base.types.multipleselection.MultipleSelection
import com.calendar.base.types.multipleselection.MultipleSelectionListener
import com.calendar.base.types.rangeslelection.RangeSelection
import com.calendar.base.types.rangeslelection.RangeSelectionListener
import com.calendar.base.types.withoutselection.WithoutSelection
import com.calendar.base.types.withoutselection.WithoutSelectionListener
import java.util.*

class MainActivity : AppCompatActivity(), WithoutSelectionListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val calendar = findViewById<CalendarView>(R.id.calendarView)

        calendar.initCalendar(
            CalendarProperties(
                regionalType = RegionalType.Jalali,
                calendarType = WithoutSelection(this),
                availableFromToday = true,
                calendarOrientation = VERTICAL
            )
        )

        calendar.submitNextDates(Calendar.MONTH, 80)
    }



}
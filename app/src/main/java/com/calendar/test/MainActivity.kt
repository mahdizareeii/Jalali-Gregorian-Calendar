package com.calendar.test

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.calendar.CalendarProperties
import com.calendar.CalendarProperties.Companion.VERTICAL
import com.calendar.CalendarView
import com.calendar.availablity.BaseAvailabilityRule
import com.calendar.availablity.RangePriceSelectionAvailabilityRule
import com.calendar.calendar.RegionalType
import com.calendar.model.Day
import com.calendar.model.DayRange
import com.calendar.types.rangeslelection.RangeSelection
import com.calendar.types.rangeslelection.RangeSelectionListener
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
                calendarOrientation = VERTICAL,
                showBubbleMessage = true,
                availabilityRule = RangePriceSelectionAvailabilityRule(
                    true,
                    true
                ),
                minDaysInRangeSelection = 2,
                customDays = arrayListOf(
                    Day(1400,12,19,false,560000.0)
                )
            )
        )

        calendar.submitNextDates(Calendar.MONTH, 3)
    }

    override fun onCheckInSelected(day: Day) {
        Toast.makeText(this, day.toString(), Toast.LENGTH_SHORT).show()
    }

    override fun onCheckOutSelected(day: Day) {
        Toast.makeText(this, day.toString(), Toast.LENGTH_SHORT).show()
    }

    private fun agendaList(month: Int, year: Int) = arrayListOf<Day>().apply {
        for (i in 1..29) {
            add(
                Day(year, month, i, false)
            )
        }
    }

    private fun agendaRangeList1() = arrayListOf(
        DayRange(
            startDate = Day(1400, 12, 5, false),
            endDate = Day(1400, 12, 12, false)
        ),
        DayRange(
            startDate = Day(1400, 12, 15, false),
            endDate = Day(1400, 12, 18, false)
        )
    )

    private fun agendaRangeList2() = arrayListOf(
        DayRange(
            startDate = Day(1400, 12, 25, false),
            endDate = Day(1401, 1, 5, false)
        ),
        DayRange(
            startDate = Day(1401, 2, 1, false),
            endDate = Day(1401, 2, 6, false)
        ),
        DayRange(
            startDate = Day(1401, 2, 10, false),
            endDate = Day(1401, 2, 15, false)
        )
    )

}
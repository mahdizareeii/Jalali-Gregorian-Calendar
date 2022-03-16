package com.calendar.test

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.calendar.CalendarProperties
import com.calendar.CalendarProperties.Companion.VERTICAL
import com.calendar.CalendarView
import com.calendar.availablity.RangePriceSelectionAvailabilityRule
import com.calendar.calendar.RegionalType
import com.calendar.model.Day
import com.calendar.model.DayRange
import com.calendar.model.agenda.AgendaDayRange
import com.calendar.types.rangeslelection.RangeSelection
import com.calendar.types.rangeslelection.RangeSelectionListener
import java.util.*

class MainActivity : AppCompatActivity(), RangeSelectionListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val calendar = findViewById<CalendarView>(R.id.calendarView)

        calendar.properties = CalendarProperties(
            regionalType = RegionalType.Jalali,
            calendarType = RangeSelection(2, true, this),
            calendarOrientation = VERTICAL,
            availabilityRule = RangePriceSelectionAvailabilityRule(
                availableFromToday = true
            ),
            agendaRangeDays = listOf(
                AgendaDayRange(
                    title = "تست 1",
                    color = "#2d2d2d",
                    agendaRangeList = agendaRangeList1(RegionalType.Jalali)
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

    override fun onSelectsRemoved() {

    }

    private fun agendaList(month: Int, year: Int, regionalType: RegionalType) =
        arrayListOf<Day>().apply {
            for (i in 1..29) {
                add(Day(year, month, i, regionalType))
            }
        }

    private fun agendaRangeList1(regionalType: RegionalType) = listOf(
        DayRange(
            startDate = Day(1400, 12, 5, regionalType),
            endDate = Day(1400, 12, 12, regionalType)
        ),
        DayRange(
            startDate = Day(1400, 12, 15, regionalType),
            endDate = Day(1400, 12, 18, regionalType)
        )
    )

    private fun agendaRangeList2(regionalType: RegionalType) = listOf(
        DayRange(
            startDate = Day(1400, 12, 25, regionalType),
            endDate = Day(1401, 1, 5, regionalType)
        ),
        DayRange(
            startDate = Day(1401, 2, 1, regionalType),
            endDate = Day(1401, 2, 6, regionalType)
        ),
        DayRange(
            startDate = Day(1401, 2, 10, regionalType),
            endDate = Day(1401, 2, 15, regionalType)
        )
    )

}
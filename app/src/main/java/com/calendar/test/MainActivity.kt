package com.calendar.test

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.calendar.CalendarProperties
import com.calendar.CalendarProperties.Companion.HORIZONTAL
import com.calendar.CalendarProperties.Companion.VERTICAL
import com.calendar.CalendarView
import com.calendar.availablity.BaseAvailabilityRule
import com.calendar.availablity.RangePriceSelectionAvailabilityRule
import com.calendar.calendar.RegionalType
import com.calendar.model.Day
import com.calendar.model.DayRange
import com.calendar.model.agenda.AgendaDayRange
import com.calendar.model.agenda.AgendaDays
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
                /*minDaysInRangeSelection = 2,*/
                availabilityRule = BaseAvailabilityRule(
                    availableFromToday = false,
                    unAvailableDisableDays = true
                ),
                /*agendaDays = arrayListOf(
                    AgendaDays(
                        "1",
                        "تست",
                        "#0091ff",
                        agendaList(
                            12,1400
                        )
                    )
                ),
                customDays = arrayListOf(
                    Day(1400, 12, 26, false, 550000.0, isHoliday = false, isDisable = false),
                    Day(1400, 12, 27, false, 12300.0, isHoliday = true, isDisable = false),
                    Day(1400, 12, 28, false, 430000.0, isHoliday = true, isDisable = true),
                    Day(1400, 12, 5, false, 78893.0, isHoliday = false, isDisable = false),
                    Day(1400, 12, 6, false, 99999.0, isHoliday = false, isDisable = false),
                    Day(1400, 12, 7, false, 100000.0, isHoliday = false, isDisable = false),
                    Day(1400, 12, 8, false, 100000.0, isHoliday = false, isDisable = true),
                    Day(1400, 12, 15, false, 456000.0, isHoliday = false, isDisable = true),
                    Day(1400, 12, 29, false, 500000.0, isHoliday = true, isDisable = false),
                    Day(1401, 1, 1, false, 100000.0, isHoliday = false, isDisable = true),
                    Day(1401, 1, 12, false, 100000.0, isHoliday = false, isDisable = true),
                )*/
                agendaRangeDays = arrayListOf(
                    AgendaDayRange(
                        id = "1",
                        title = "روز های پر تردد",
                        color = "#0091ff",
                        agendaRangeList = agendaRangeList1()
                    ),
                    AgendaDayRange(
                        id = "2",
                        title = "روزهای پر ترافیک",
                        color = "#192B37",
                        agendaRangeList = agendaRangeList2()
                    )
                )
            )
        )

        calendar.submitNextDates(Calendar.MONTH, 80)
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
            startDate = Day(1400, 12, 1, false),
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
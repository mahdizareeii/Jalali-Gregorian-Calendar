package com.calendar.test

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.calendar.CalendarProperties
import com.calendar.CalendarProperties.Companion.VERTICAL
import com.calendar.CalendarView
import com.calendar.base.availablity.RangePriceSelectionAvailabilityRule
import com.calendar.base.calendar.RegionalType
import com.calendar.base.model.AgendaDays
import com.calendar.base.model.DayItem
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
                calendarOrientation = VERTICAL,
                showDaysPrice = true,
                minDaysInRangeSelection = 2,
                availabilityRule = RangePriceSelectionAvailabilityRule(
                    availableFromToday = true,
                    unAvailableDisableDays = true
                ),
                customDays = arrayListOf(
                    DayItem(1400, 11, 26, false, 550000.0, isHoliday = false, isDisable = false),
                    DayItem(1400, 11, 27, false, 12300.0, isHoliday = true, isDisable = false),
                    DayItem(1400, 11, 28, false, 430000.0, isHoliday = true, isDisable = true),
                    DayItem(1400, 12, 5, false, 78893.0, isHoliday = false, isDisable = false),
                    DayItem(1400, 12, 6, false, 99999.0, isHoliday = false, isDisable = false),
                    DayItem(1400, 12, 7, false, 100000.0, isHoliday = false, isDisable = false),
                    DayItem(1400, 12, 8, false, 100000.0, isHoliday = false, isDisable = true),
                    DayItem(1400, 12, 15, false, 456000.0, isHoliday = false, isDisable = true),
                    DayItem(1400, 12, 29, false, 500000.0, isHoliday = true, isDisable = false),
                    DayItem(1401, 1, 1, false, 100000.0, isHoliday = false, isDisable = true),
                ),
                agendaDays = arrayListOf(
                    AgendaDays(
                        id = "0",
                        title = "روز های تحت دستور",
                        color = "#fb3449",
                        daysList = listOf(
                            DayItem(1400,11,29,false),
                            DayItem(1400,11,30,false),
                            DayItem(1400,12,1,false),
                            DayItem(1401,1,1,false),
                            DayItem(1401,1,2,false),
                            DayItem(1401,1,3,false),
                            DayItem(1401,1,4,false),
                            DayItem(1401,2,4,false),
                        )
                    )
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
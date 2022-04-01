package com.calendar.calendar

import androidx.annotation.IntRange
import com.calendar.model.Day
import com.calendar.model.Month
import java.util.*

class MyGregorianCalendar() : BaseCalendar() {

    private val calendar = GregorianCalendar()

    constructor(
        gregorianYear: Int,
        @IntRange(from = 1, to = 12) gregorianMonth: Int,
        @IntRange(from = 1, to = 31) gregorianDay: Int
    ) : this() {
        calendar.set(gregorianYear, gregorianMonth - 1, gregorianDay)
    }

    override val nameOfMonths: Array<String>
        get() = arrayOf(
            "January", "February", "March", "April", "May", "June",
            "July", "August", "September", "October", "November", "December"
        )
    override val dayOfWeeks: Array<String>
        get() = arrayOf(
            "sunday",
            "monday",
            "tuesday",
            "wednesday",
            "thursday",
            "friday",
            "saturday"
        )

    override fun set(field: Int, value: Int) {
        when (field) {
            Calendar.YEAR -> calendar.set(field, value)
            Calendar.MONTH -> calendar.set(field, value - 1)
            Calendar.DAY_OF_MONTH -> calendar.set(field, value)
            else -> {
            }
        }
    }

    override fun get(field: Int) = when (field) {
        Calendar.YEAR -> calendar.get(field)
        Calendar.MONTH -> calendar.get(field) + 1
        Calendar.DAY_OF_MONTH -> calendar.get(field)
        else -> 0
    }

    override fun clear() {
        calendar.clear()
    }

    override fun getDayOfWeek(): Int = calendar.get(Calendar.DAY_OF_WEEK)

    override fun getDayOfWeekAsString(): String = dayOfWeeks[getDayOfWeek() - 1]

    override fun getMonth(): Int = get(Calendar.MONTH)

    override fun getMonthName(): String {
        return nameOfMonths.getOrNull(get(Calendar.MONTH) - 1) ?: "Unknown"
    }

    override fun getYear(): Int = get(Calendar.YEAR)

    override fun getToday(): Day {
        val todayCalendar = Calendar.getInstance()
        return Day(
            year = todayCalendar.get(Calendar.YEAR),
            month = todayCalendar.get(Calendar.MONTH) + 1,
            day = todayCalendar.get(Calendar.DAY_OF_MONTH),
            regionalType = RegionalType.Gregorian
        )
    }

    override fun getTime(): Date {
        return calendar.time
    }

    override fun getNextDates(field: Int, value: Int): List<Month> {
        val today = Calendar.getInstance()
        val next = Calendar.getInstance().apply {
            set(field, get(field) + value)
        }

        return getMonthsBetweenDateRange(
            startDay = Day(
                year = today.get(Calendar.YEAR),
                month = today.get(Calendar.MONTH) + 1,
                day = 1,
                regionalType = RegionalType.Gregorian
            ),
            endDay = Day(
                year = next.get(Calendar.YEAR),
                month = next.get(Calendar.MONTH) + 1,
                day = 1,
                regionalType = RegionalType.Gregorian
            )
        )
    }

    override fun generateDays(): List<Int> {
        val days = ArrayList<Int>()
        val countOfDays = calendar.getActualMaximum(Calendar.DAY_OF_MONTH)

        //shift days
        repeat(getFirstDayPositionInWeek()) {
            days.add(-1)
        }

        for (day in 1..countOfDays) {
            days.add(day)
        }

        return days
    }

    override fun getNewInstanceOfCalendar(): BaseCalendar = MyGregorianCalendar()

    override fun getFirstDayPositionInWeek(): Int {
        calendar.set(Calendar.DAY_OF_MONTH, 1)
        val position = calendar.get(Calendar.DAY_OF_WEEK)
        return if (position == 7) 0 else position
    }

}
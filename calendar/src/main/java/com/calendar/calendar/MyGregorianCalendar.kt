package com.calendar.calendar

import com.calendar.model.Day
import com.calendar.model.Month
import java.util.*

class MyGregorianCalendar : BaseCalendar() {

    private val calendar = GregorianCalendar()

    override val nameOfMonths: List<String>
        get() = listOf(
            "January", "February", "March", "April", "May", "June",
            "July", "August", "September", "October", "November", "December"
        )

    override fun set(field: Int, value: Int) {
        calendar.set(field, value)
    }

    override fun get(field: Int) = calendar.get(field)

    override fun clear() {
        calendar.clear()
    }

    override fun getYear(): Int = calendar.get(Calendar.YEAR)

    override fun getMonth(): Int = calendar.get(Calendar.MONTH)

    override fun getMonthName(): String {
        return nameOfMonths.getOrNull(calendar.get(Calendar.MONTH)) ?: "Unknown"
    }

    override fun getNewInstanceOfCalendar(): BaseCalendar = MyGregorianCalendar()

    override fun getFirstDayPositionInWeek(): Int {
        calendar.set(Calendar.DAY_OF_MONTH, 1)
        val position = calendar.get(Calendar.DAY_OF_WEEK)
        return if (position == 7) 0 else position
    }

    override fun getToday(): Day {
        val todayCalendar = Calendar.getInstance()
        return Day(
            todayCalendar.get(Calendar.YEAR),
            todayCalendar.get(Calendar.MONTH) + 1,
            todayCalendar.get(Calendar.DAY_OF_MONTH)
        )
    }

    override fun getNextDates(field: Int, value: Int): List<Month> {
        val today = Calendar.getInstance()
        val next = Calendar.getInstance().apply {
            set(field, get(field) + value)
        }

        return getMonthsBetweenDateRange(
            startDay = Day(today.get(Calendar.YEAR), today.get(Calendar.MONTH) + 1, 1),
            endDay = Day(next.get(Calendar.YEAR), next.get(Calendar.MONTH) + 1, 1)
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

}
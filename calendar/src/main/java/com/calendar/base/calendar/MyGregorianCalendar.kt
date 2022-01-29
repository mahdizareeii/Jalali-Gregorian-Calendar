package com.calendar.base.calendar

import java.util.*

class MyGregorianCalendar : BaseCalendar() {

    private val calendar = GregorianCalendar()

    override val nameOfMonths: List<String>
        get() = listOf(
            "January", "February", "March", "April", "May", "June",
            "July", "August", "September", "October", "November", "December"
        )

    override fun getDisplayedMonthName(): String {
        return nameOfMonths.getOrNull(calendar.get(Calendar.MONTH)) ?: "Unknown"
    }

    override fun firstDayPositionInWeek(): Int {
        calendar.set(Calendar.DAY_OF_MONTH, 1)
        val position = calendar.get(Calendar.DAY_OF_WEEK)
        return if (position == 7) 0 else position
    }

    override fun generateDays(): List<Int> {
        val days = ArrayList<Int>()
        val countOfDays = calendar.getActualMaximum(Calendar.DAY_OF_MONTH)

        //shift days
        repeat(firstDayPositionInWeek()) {
            days.add(-1)
        }

        for (day in 1..countOfDays) {
            days.add(day)
        }

        return days
    }

    override fun getYearName(): String = calendar.get(Calendar.YEAR).toString()

    override fun set(field: Int, value: Int) {
        calendar.set(field, value)
    }

    override fun get(field: Int) = calendar.get(field)

    override fun clear() {
        calendar.clear()
    }

    override fun getNewInstanceOfCalendar(): BaseCalendar = MyGregorianCalendar()

}
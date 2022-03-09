package com.calendar.calendar

import com.calendar.model.Day
import com.calendar.model.Month

abstract class BaseCalendar {

    abstract val nameOfMonths: List<String>
    abstract fun set(field: Int, value: Int)
    abstract fun get(field: Int): Int?
    abstract fun clear()
    abstract fun getYear(): Int
    abstract fun getMonth(): Int
    abstract fun getMonthName(): String
    protected abstract fun getNewInstanceOfCalendar(): BaseCalendar

    /** you must set month like this
     *  calendar.set(Calendar.MONTH, month)
     *  before call this method
     *
     *  @return position of day in week
     */
    abstract fun getFirstDayPositionInWeek(): Int

    abstract fun getToday(): Day

    /**
     *  you can increase up month and year with set field and you will get next dates
     *  @param field the field that you want increase up
     *  @param value the value that increase up field
     *  @return a list that contain months
     */
    abstract fun getNextDates(field: Int, value: Int): List<Month>

    /**
     * @return list of days of month
     */
    abstract fun generateDays(): List<Int>

    fun getMonthsBetweenDateRange(
        startDay: Day,
        endDay: Day
    ): List<Month> {
        val months = ArrayList<Month>()
        when {
            startDay.year == endDay.year -> {
                for (month in startDay.month..endDay.month)
                    months.add(Month(getNewInstanceOfCalendar(), month, startDay.year))
            }

            startDay.year < endDay.year -> {
                for (year in startDay.year..endDay.year) {
                    when (year) {
                        startDay.year -> {
                            for (month in startDay.month..12)
                                months.add(Month(getNewInstanceOfCalendar(), month, year))
                        }
                        endDay.year -> {
                            for (month in 1..endDay.month)
                                months.add(Month(getNewInstanceOfCalendar(), month, year))
                        }
                        else -> {
                            for (month in 1..12)
                                months.add(Month(getNewInstanceOfCalendar(), month, year))
                        }
                    }
                }
            }
        }
        return months
    }
}
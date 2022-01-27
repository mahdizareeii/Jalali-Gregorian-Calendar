package com.calendar.base.calendar

import com.calendar.base.model.MonthItem

abstract class BaseCalendar {

    abstract val nameOfMonths: List<String>

    abstract fun getDisplayedMonthName(value: Int): String

    /** you must set month like this
     *  'calendar.set(Calendar.MONTH, month)'
     *  before call this method
     */
    abstract fun firstDayPositionInWeek(): Int
    abstract fun generateDays(month: Int): List<Int>
    abstract fun getYearName(): String

    abstract fun getNextDates(field: Int, value: Int): List<MonthItem>
    abstract fun set(field: Int, value: Int)
    abstract fun get(field: Int):Int
}
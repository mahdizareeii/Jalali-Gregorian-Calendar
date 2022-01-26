package com.calendar.base.calendar

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
}
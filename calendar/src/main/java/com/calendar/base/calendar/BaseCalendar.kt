package com.calendar.base.calendar

import java.util.*

abstract class BaseCalendar : GregorianCalendar {

    constructor()
    constructor(zone: TimeZone) : super(zone)
    constructor(locale: Locale) : super(locale)
    constructor(zone: TimeZone, locale: Locale) : super(locale)
    constructor(year: Int, month: Int, dayOfMonth: Int) : super(year, month, dayOfMonth)
    constructor(year: Int, month: Int, dayOfMonth: Int, hourOfDay: Int, minute: Int) : super(
        year,
        month,
        dayOfMonth,
        hourOfDay,
        minute
    )

    constructor(
        year: Int,
        month: Int,
        dayOfMonth: Int,
        hourOfDay: Int,
        minute: Int,
        second: Int
    ) : super(
        year,
        month,
        dayOfMonth,
        hourOfDay,
        minute,
        second
    )

    abstract val nameOfMonths: List<String>

    fun getDisplayedMonthName(value: Int): String {
        return nameOfMonths.getOrNull(value) ?: "Unknown"
    }

    /** you must set month with like this
     *  calendar.set(Calendar.MONTH, month)
     *  before call this method
     */
    fun firstDayPositionInWeek(): Int {
        set(Calendar.DAY_OF_MONTH, 1)
        val position = get(Calendar.DAY_OF_WEEK)
        return if (position == 7) 0 else position
    }
}
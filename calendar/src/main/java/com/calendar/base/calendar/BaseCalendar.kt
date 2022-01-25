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


}
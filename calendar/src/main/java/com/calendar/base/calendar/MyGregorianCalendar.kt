package com.calendar.base.calendar

import java.util.*

class MyGregorianCalendar : BaseCalendar {

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

    override val nameOfMonths: List<String>
        get() = listOf(
            "January", "February", "March", "April", "May", "June",
            "July", "August", "September", "October", "November", "December"
        )

}
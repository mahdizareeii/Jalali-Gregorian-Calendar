package com.calendar.base.calendar

enum class RegionalType(internal val calendar: BaseCalendar) {
    Jalali(MyJalaliCalendar()),
    Gregorian(MyGregorianCalendar())
}
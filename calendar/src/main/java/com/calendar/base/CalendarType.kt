package com.calendar.base

import com.calendar.base.calendar.BaseCalendar
import com.calendar.base.calendar.MyGregorianCalendar
import com.calendar.base.calendar.MyJalaliCalendar

enum class CalendarType(internal val calendar: BaseCalendar) {
    Jalali(MyJalaliCalendar()),
    Gregorian(MyGregorianCalendar())
}
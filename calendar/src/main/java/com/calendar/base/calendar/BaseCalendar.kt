package com.calendar.base.calendar

import com.calendar.base.model.MonthItem
import java.util.*

abstract class BaseCalendar {

    abstract val nameOfMonths: List<String>

    abstract fun getDisplayedMonthName(): String

    /** you must set month like this
     *  'calendar.set(Calendar.MONTH, month)'
     *  before call this method
     */
    abstract fun firstDayPositionInWeek(): Int
    abstract fun generateDays(): List<Int>
    abstract fun getYearName(): String
    abstract fun set(field: Int, value: Int)
    abstract fun get(field: Int): Int
    abstract fun clear()
    protected abstract fun getNewInstanceOfCalendar(): BaseCalendar

    /**
     *  you can increase up month and year with set field and you will get next dates
     */
    fun getNextDates(field: Int, value: Int): List<MonthItem> {
        val months = ArrayList<MonthItem>()
        val today = Calendar.getInstance()
        val next = Calendar.getInstance().apply {
            set(field, get(field) + value)
        }

        val todayYear = today.get(Calendar.YEAR)
        val todayMonth = today.get(Calendar.MONTH)
        val nextYear = next.get(Calendar.YEAR)
        val nextMonth = next.get(Calendar.MONTH)

        if (todayYear == nextYear) {
            if (field == Calendar.MONTH)
                for (month in todayMonth..nextMonth) {
                    months.add(MonthItem(getNewInstanceOfCalendar(), month, todayYear))
                }
        } else {
            if (field == Calendar.YEAR)
                for (year in todayYear..nextYear) {
                    for (month in 0..11)
                        months.add(MonthItem(getNewInstanceOfCalendar(), month, year))
                }
            else if (field == Calendar.MONTH) {
                var tempMonth = value
                var tempYear = todayYear
                while (tempMonth >= 12) {
                    for (month in 0..11)
                        months.add(MonthItem(getNewInstanceOfCalendar(), month, tempYear))
                    tempMonth -= 12
                    tempYear++
                }

                if (tempMonth != 0)
                    for (month in 0..tempMonth)
                        months.add(MonthItem(getNewInstanceOfCalendar(), month, tempYear))
            }
        }
        return months
    }

    fun gregorianToJalali(year: Int, month: Int, day: Int): IntArray {
        val gregorianDayMonth: IntArray =
            intArrayOf(0, 31, 59, 90, 120, 151, 181, 212, 243, 273, 304, 334)
        val gy2 = if (month > 2) (year + 1) else year
        var days =
            355666 + (365 * year) + ((gy2 + 3) / 4) - ((gy2 + 99) / 100) + ((gy2 + 399) / 400) + day + gregorianDayMonth[month - 1]
        var jalaliYear = -1595 + (33 * (days / 12053))
        days %= 12053
        jalaliYear += 4 * (days / 1461)
        days %= 1461
        if (days > 365) {
            jalaliYear += ((days - 1) / 365)
            days = (days - 1) % 365
        }
        val jalaliMonth: Int
        val jalaliDay: Int
        if (days < 186) {
            jalaliMonth = 1 + (days / 31)
            jalaliDay = 1 + (days % 31)
        } else {
            jalaliMonth = 7 + ((days - 186) / 30)
            jalaliDay = 1 + ((days - 186) % 30)
        }
        return intArrayOf(jalaliYear, jalaliMonth, jalaliDay)
    }

    fun jalaliToGregorian(year: Int, month: Int, day: Int): IntArray {
        val yearTemp = year + 1595
        var days =
            -355668 + (365 * yearTemp) + ((yearTemp / 33) * 8) + (((yearTemp % 33) + 3) / 4) + day + (if (month < 7) ((month - 1) * 31) else (((month - 7) * 30) + 186))
        var gregorianYear = 400 * (days / 146097)
        days %= 146097
        if (days > 36524) {
            gregorianYear += 100 * (--days / 36524)
            days %= 36524
            if (days >= 365) days++
        }
        gregorianYear += 4 * (days / 1461)
        days %= 1461
        if (days > 365) {
            gregorianYear += ((days - 1) / 365)
            days = (days - 1) % 365
        }
        var gregorianDay: Int = days + 1
        val sal_a: IntArray = intArrayOf(
            0,
            31,
            if ((gregorianYear % 4 == 0 && gregorianYear % 100 != 0) || (gregorianYear % 400 == 0)) 29 else 28,
            31,
            30,
            31,
            30,
            31,
            31,
            30,
            31,
            30,
            31
        )
        var gregorianMonth = 0
        while (gregorianMonth < 13 && gregorianDay > sal_a[gregorianMonth]) gregorianDay -= sal_a[gregorianMonth++]
        return intArrayOf(gregorianYear, gregorianMonth, gregorianDay)
    }
}
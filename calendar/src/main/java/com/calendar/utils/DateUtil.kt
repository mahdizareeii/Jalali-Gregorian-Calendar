package com.calendar.utils

import com.calendar.model.Day
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

object DateUtil {

    /**
     * @return IntArray of jalali date for example : intArrayOf(1450, 1, 1)
     */
    fun gregorianToJalali(day: Day?): IntArray? {
        day ?: return null
        return gregorianToJalali(day.year, day.month, day.day)
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

    /**
     * @return IntArray of gregorian date for example : intArrayOf(2050, 1, 1)
     */
    fun jalaliToGregorian(day: Day?): IntArray?{
        day ?: return null
        return jalaliToGregorian(day.year, day.month, day.day)
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

    /**
     * @return longArrayOf(differenceInYears,differenceInDays,differenceInHours,differenceInMinutes,differenceInSeconds)
     */
    fun diffDaysJalali(start: Day?, end: Day?): LongArray {
        val startGregorian = jalaliToGregorian(
            year = start?.year ?: 0,
            month = start?.month ?: 0,
            day = start?.day ?: 0
        )

        val endGregorian = jalaliToGregorian(
            year = end?.year ?: 0,
            month = end?.month ?: 0,
            day = end?.day ?: 0
        )

        val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        sdf.timeZone = TimeZone.getTimeZone("UTC")
        val startDate = sdf.parse("${startGregorian[0]}-${startGregorian[1]}-${startGregorian[2]}")
        val endDate = sdf.parse("${endGregorian[0]}-${endGregorian[1]}-${endGregorian[2]}")

        return operateDiffs(startDate, endDate)
    }

    /**
     * @return longArrayOf(differenceInYears,differenceInDays,differenceInHours,differenceInMinutes,differenceInSeconds)
     */
    fun diffDaysGregorian(start: Day?, end: Day?): LongArray {
        val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.US)
        sdf.timeZone = TimeZone.getTimeZone("UTC")
        val startDate = sdf.parse("${start?.year ?: 0}-${start?.month ?: 0}-${start?.day ?: 0}")
        val endDate = sdf.parse("${end?.year ?: 0}-${end?.month ?: 0}-${end?.day ?: 0}")

        return operateDiffs(startDate, endDate)
    }

    private fun operateDiffs(start: Date?, end: Date?): LongArray {
        val differenceInTime = (end?.time ?: 0) - (start?.time ?: 0)

        val differenceInSeconds = (TimeUnit.MILLISECONDS.toSeconds(differenceInTime) % 60)
        val differenceInMinutes = (TimeUnit.MILLISECONDS.toMinutes(differenceInTime) % 60)
        val differenceInHours = (TimeUnit.MILLISECONDS.toHours(differenceInTime) % 24)
        val differenceInDays = (TimeUnit.MILLISECONDS.toDays(differenceInTime) % 365)
        val differenceInYears = (TimeUnit.MILLISECONDS.toDays(differenceInTime) / 365L)

        return longArrayOf(
            differenceInYears,
            differenceInDays,
            differenceInHours,
            differenceInMinutes,
            differenceInSeconds
        )
    }

}
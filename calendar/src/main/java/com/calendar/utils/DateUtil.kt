package com.calendar.utils

import com.calendar.base.calendar.MyJalaliCalendar
import com.calendar.base.model.DayItem
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

object DateUtil {
    private val jalali = MyJalaliCalendar()

    fun diffDaysJalali(start: DayItem?, end: DayItem?): Int {
        val startGregorian = jalali.jalaliToGregorian(
            year = start?.year ?: 0,
            month = start?.month ?: 0,
            day = start?.day ?: 0
        )

        val endGregorian = jalali.jalaliToGregorian(
            year = end?.year ?: 0,
            month = end?.month ?: 0,
            day = end?.day ?: 0
        )

        val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        sdf.timeZone = TimeZone.getTimeZone("UTC")
        val startDate = sdf.parse("${startGregorian[0]}-${startGregorian[1]}-${startGregorian[2]}")
        val endDate = sdf.parse("${endGregorian[0]}-${endGregorian[1]}-${endGregorian[2]}")

        return operateDiffs(startDate, endDate)[1].toInt()
    }

    fun diffDaysGregorian(start: DayItem?, end: DayItem?): Int {
        val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.US)
        sdf.timeZone = TimeZone.getTimeZone("UTC")
        val startDate = sdf.parse("${start?.year ?: 0}-${start?.month ?: 0}-${start?.day ?: 0}")
        val endDate = sdf.parse("${end?.year ?: 0}-${end?.month ?: 0}-${end?.day ?: 0}")

        return operateDiffs(startDate, endDate)[1].toInt()
    }

    private fun operateDiffs(start: Date?, end: Date?): LongArray {
        val differenceInTime = (end?.time ?: 0) - (start?.time ?: 0)

        val differenceInSeconds: Long = (TimeUnit.MILLISECONDS.toSeconds(differenceInTime) % 60)
        val differenceInMinutes: Long = (TimeUnit.MILLISECONDS.toMinutes(differenceInTime) % 60)
        val differenceInHours: Long = (TimeUnit.MILLISECONDS.toHours(differenceInTime) % 24)
        val differenceInDays: Long = (TimeUnit.MILLISECONDS.toDays(differenceInTime) % 365)
        val differenceInYears: Long = (TimeUnit.MILLISECONDS.toDays(differenceInTime) / 365L)

        return longArrayOf(
            differenceInYears,
            differenceInDays,
            differenceInHours,
            differenceInMinutes,
            differenceInSeconds
        )
    }

}
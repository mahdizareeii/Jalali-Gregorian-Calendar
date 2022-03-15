package com.calendar.calendar

import com.calendar.model.Day
import com.calendar.model.Month
import com.calendar.utils.DateUtil
import java.util.*

class MyJalaliCalendar() : BaseCalendar() {

    var persianDate: IntArray? = intArrayOf(0, 0, 0)
    var persianDateAsString: String? = null

    constructor(gregorianYear: Int, gregorianMonth: Int, gregorianDay: Int) : this() {
        persianDate = DateUtil.gregorianToJalali(
            year = gregorianYear,
            month = gregorianMonth - 1,
            day = gregorianDay
        )
        persianDateAsString = persianDate?.joinToString("-")
    }

    constructor(gregorianDate: String?, separator: String) : this() {
        val date = gregorianDate?.split(separator)
        persianDate = DateUtil.gregorianToJalali(
            year = date?.getOrNull(0)?.toIntOrNull(),
            month = date?.getOrNull(1)?.toIntOrNull(),
            day = date?.getOrNull(2)?.toIntOrNull()
        )
        persianDateAsString = persianDate?.joinToString("-")
    }

    override val nameOfMonths: Array<String>
        get() = arrayOf(
            "فروردین", "اردیبهشت", "خرداد", "تیر", "مرداد", "شهریور",
            "مهر", "آبان", "آذر", "دی", "بهمن", "اسفند"
        )

    override val dayOfWeeks: Array<String>
        get() = arrayOf(
            "یکشنبه",
            "دوشنبه",
            "سه‌شنبه",
            "چهارشنبه",
            "پنجشنبه",
            "جمعه",
            "شنبه",
        )

    override fun set(field: Int, value: Int) {
        when (field) {
            Calendar.YEAR -> persianDate?.set(0, value)
            Calendar.MONTH -> persianDate?.set(1, value)
            Calendar.DAY_OF_MONTH -> persianDate?.set(2, value)
            else -> {
            }
        }
    }

    override fun get(field: Int) = when (field) {
        Calendar.YEAR -> persianDate?.getOrNull(0)
        Calendar.MONTH -> persianDate?.getOrNull(1)
        Calendar.DAY_OF_MONTH -> persianDate?.getOrNull(2)
        else -> 0
    }

    override fun clear() {
        persianDate = intArrayOf(0, 0, 0)
    }

    override fun getDayOfWeek(): Int {
        val gregorian = DateUtil.jalaliToGregorian(
            persianDate?.getOrNull(0) ?: 0,
            (persianDate?.getOrNull(1) ?: 0) + 1,
            persianDate?.getOrNull(2) ?: 0,
        )

        val calendar = Calendar.getInstance().apply {
            set(Calendar.YEAR, gregorian?.getOrNull(0) ?: 0)
            set(Calendar.MONTH, (gregorian?.getOrNull(1) ?: 0) - 1)
            set(Calendar.DAY_OF_MONTH, gregorian?.getOrNull(2) ?: 0)
        }

        return calendar.get(Calendar.DAY_OF_WEEK)
    }

    override fun getDayOfWeekAsString(): String = dayOfWeeks[getDayOfWeek() - 1]

    override fun getMonth(): Int = (persianDate?.getOrNull(1) ?: -1)

    override fun getMonthName(): String {
        return nameOfMonths.getOrNull(
            (persianDate?.getOrNull(1) ?: -1)
        ) ?: "نا شناخته"
    }

    override fun getYear(): Int = persianDate?.getOrNull(0) ?: -1

    override fun getToday(): Day {
        val todayCalendar = Calendar.getInstance()
        val today = DateUtil.gregorianToJalali(
            todayCalendar.get(Calendar.YEAR),
            todayCalendar.get(Calendar.MONTH) + 1,
            todayCalendar.get(Calendar.DAY_OF_MONTH)
        )
        return Day(
            today?.getOrNull(0) ?: 0,
            today?.getOrNull(1) ?: 0,
            today?.getOrNull(2) ?: 0
        )
    }

    override fun getTime(): Date {
        val gregorian = DateUtil.jalaliToGregorian(
            persianDate?.getOrNull(0) ?: 0,
            (persianDate?.getOrNull(1) ?: 0) + 1,
            persianDate?.getOrNull(2) ?: 0,
        )
        return Calendar.getInstance().apply {
            set(Calendar.YEAR, gregorian?.getOrNull(0) ?: 0)
            set(Calendar.MONTH, (gregorian?.getOrNull(1) ?: 0) -1)
            set(Calendar.DAY_OF_MONTH, gregorian?.getOrNull(2) ?: 0)
        }.time
    }

    override fun getNextDates(field: Int, value: Int): List<Month> {
        val today = Calendar.getInstance()
        val next = Calendar.getInstance().apply {
            set(field, get(field) + value)
        }

        val todayJalali = DateUtil.gregorianToJalali(
            today.get(Calendar.YEAR),
            today.get(Calendar.MONTH) + 1,
            today.get(Calendar.DAY_OF_MONTH)
        )

        val nextJalali = DateUtil.gregorianToJalali(
            next.get(Calendar.YEAR),
            next.get(Calendar.MONTH) + 1,
            next.get(Calendar.DAY_OF_MONTH)
        )

        return getMonthsBetweenDateRange(
            startDay = Day(
                todayJalali?.getOrNull(0) ?: 0,
                todayJalali?.getOrNull(1) ?: 0,
                1
            ),
            endDay = Day(
                nextJalali?.getOrNull(0) ?: 0,
                nextJalali?.getOrNull(1) ?: 0,
                1
            )
        )
    }

    override fun generateDays(): List<Int> {
        val days = ArrayList<Int>()

        val countOfDays = getJalaliMonthCount(
            persianDate?.getOrNull(0) ?: 0,
            (persianDate?.getOrNull(1) ?: 0) + 1
        )

        //shift days
        repeat(getFirstDayPositionInWeek()) {
            days.add(-1)
        }

        for (day in 1..countOfDays) {
            days.add(day)
        }

        return days
    }

    override fun getNewInstanceOfCalendar(): BaseCalendar = MyJalaliCalendar()

    override fun getFirstDayPositionInWeek(): Int {
        val position = getDayOfWeek()
        return if (position == 7) 0 else position
    }

    private fun getJalaliMonthCount(year: Int, month: Int): Int {
        return if (month <= 6) {
            31
        } else if (month in 7..11) {
            30
        } else {
            if (isLeapYear(year))
                30
            else 29
        }
    }

    private fun isLeapYear(year: Int): Boolean {
        return arrayOf(1, 5, 9, 13, 17, 22, 26, 30).any {
            it == year % 33
        }
    }
}
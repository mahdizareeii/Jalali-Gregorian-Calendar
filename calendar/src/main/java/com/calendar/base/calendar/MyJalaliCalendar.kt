package com.calendar.base.calendar

import com.calendar.base.model.Day
import com.calendar.base.model.Month
import com.calendar.utils.DateUtil
import java.util.*

class MyJalaliCalendar() : BaseCalendar() {

    var persianDate: IntArray = intArrayOf(0, 0, 0)
    var persianDateAsString: String? = null

    constructor(year: Int, month: Int, day: Int) : this() {
        persianDate = DateUtil.gregorianToJalali(
            year,
            month,
            day
        )
        persianDateAsString = persianDate.joinToString("-")
    }

    override val nameOfMonths: List<String>
        get() = listOf(
            "فروردین",
            "اردیبهشت",
            "خرداد",
            "تیر",
            "مرداد",
            "شهریور",
            "مهر",
            "آبان",
            "آذر",
            "دی",
            "بهمن",
            "اسفند"
        )

    override fun set(field: Int, value: Int) {
        when (field) {
            Calendar.YEAR -> persianDate[0] = value
            Calendar.MONTH -> persianDate[1] = value
            Calendar.DAY_OF_MONTH -> persianDate[2] = value
            else -> {
            }
        }
    }

    override fun get(field: Int) = when (field) {
        Calendar.YEAR -> persianDate[0]
        Calendar.MONTH -> persianDate[1]
        Calendar.DAY_OF_MONTH -> persianDate[3]
        else -> 0
    }

    override fun clear() {
        persianDate = intArrayOf(0, 0, 0)
    }

    override fun getYear(): Int = persianDate.getOrNull(0) ?: -1

    override fun getMonth(): Int = (persianDate.getOrNull(1) ?: -1)

    override fun getMonthName(): String {
        return nameOfMonths.getOrNull(
            (persianDate.getOrNull(1) ?: -1)
        ) ?: "نا شناخته"
    }

    override fun getNewInstanceOfCalendar(): BaseCalendar = MyJalaliCalendar()

    override fun getFirstDayPositionInWeek(): Int {
        val position = getDayOfWeek()
        return if (position == 7) 0 else position
    }

    override fun getToday(): Day {
        val todayCalendar = Calendar.getInstance()
        val today = DateUtil.gregorianToJalali(
            todayCalendar.get(Calendar.YEAR),
            todayCalendar.get(Calendar.MONTH) + 1,
            todayCalendar.get(Calendar.DAY_OF_MONTH)
        )
        return Day(today[0], today[1], today[2], false)
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

        val todayYear = todayJalali[0]
        val todayMonth = todayJalali[1]
        val nextYear = nextJalali[0]
        val nextMonth = nextJalali[1]

        return getMonthsBetweenDateRange(
            field,
            value,
            todayMonth,
            todayYear,
            nextMonth,
            nextYear
        )
    }

    override fun generateDays(): List<Int> {
        val days = ArrayList<Int>()

        val countOfDays = getJalaliMonthCount(
            persianDate[0],
            persianDate[1] + 1
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

    private fun getDayOfWeek(): Int {
        val gregorian = DateUtil.jalaliToGregorian(
            persianDate.getOrNull(0) ?: 0,
            (persianDate.getOrNull(1) ?: 0) + 1,
            1,
        )

        val calendar = Calendar.getInstance().apply {
            set(Calendar.YEAR, gregorian[0])
            set(Calendar.MONTH, gregorian[1] - 1)
            set(Calendar.DAY_OF_MONTH, gregorian[2])
        }

        return calendar.get(Calendar.DAY_OF_WEEK)
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
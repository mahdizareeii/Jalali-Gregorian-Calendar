package com.calendar.base.calendar

import com.calendar.base.model.MonthItem
import java.util.*

class MyJalaliCalendar : BaseCalendar() {

    private val calendar = GregorianCalendar()

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

    override fun getDisplayedMonthName(value: Int): String {
        val monthIndic = gregorianToJalali(
            calendar.get(Calendar.YEAR),
            value + 1,
            calendar.get(Calendar.DAY_OF_MONTH)
        )[1]

        return nameOfMonths.getOrNull(monthIndic - 1) ?: "نا شناخته"
    }

    override fun firstDayPositionInWeek(): Int {
        calendar.set(Calendar.DAY_OF_MONTH, 1)
        val position = calendar.get(Calendar.DAY_OF_WEEK)
        return if (position == 7) 0 else position
    }

    override fun generateDays(month: Int): List<Int> {
        val days = ArrayList<Int>()
        calendar.set(Calendar.MONTH, month)
        val countOfDays = calendar.getActualMaximum(Calendar.DAY_OF_MONTH)

        //shift days
        repeat(firstDayPositionInWeek()) {
            days.add(-1)
        }

        for (day in 1..countOfDays) {
            days.add(
                gregorianToJalali(
                    calendar.get(Calendar.YEAR),
                    calendar.get(Calendar.MONTH) + 1,
                    day
                )[2]
            )
        }

        return days
    }

    override fun getYearName(): String = gregorianToJalali(
        calendar.get(Calendar.YEAR),
        calendar.get(Calendar.MONTH) + 1,
        calendar.get(Calendar.DAY_OF_MONTH)
    )[0].toString()

    override fun getNextDates(field: Int, value: Int): List<MonthItem> {
        val months = ArrayList<MonthItem>()
        val today = Calendar.getInstance()
        val next = Calendar.getInstance().apply {
            set(field, get(field) + value)
        }

        //TODO refactor here like gregorian
        val todayYear = today.get(Calendar.YEAR)
        val todayMonth = today.get(Calendar.MONTH)
        val nextYear = next.get(Calendar.YEAR)
        val nextMonth = next.get(Calendar.MONTH)
        for (month in todayMonth until nextMonth) {
            months.add(MonthItem(MyJalaliCalendar(), month,todayYear))
        }
        return months
    }

    override fun set(field: Int, value: Int) {
        calendar.set(field, value)
    }

    private fun gregorianToJalali(year: Int, month: Int, day: Int): IntArray {
        val g_d_m: IntArray = intArrayOf(0, 31, 59, 90, 120, 151, 181, 212, 243, 273, 304, 334)
        val gy2 = if (month > 2) (year + 1) else year
        var days =
            355666 + (365 * year) + ((gy2 + 3) / 4) - ((gy2 + 99) / 100) + ((gy2 + 399) / 400) + day + g_d_m[month - 1]
        var jalaliYear = -1595 + (33 * (days / 12053))
        days %= 12053
        jalaliYear += 4 * (days / 1461)
        days %= 1461
        if (days > 365) {
            jalaliYear += ((days - 1) / 365)
            days = (days - 1) % 365
        }
        val jalaliMonth: Int;
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

    private fun jalaliToGregorian(year: Int, month: Int, day: Int): IntArray {
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
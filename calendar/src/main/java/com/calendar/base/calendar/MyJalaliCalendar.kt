package com.calendar.base.calendar

import com.calendar.base.model.DayItem
import com.calendar.utils.DateUtil
import java.util.*

class MyJalaliCalendar : BaseCalendar() {

    private val calendar = GregorianCalendar()
    var persianDate: IntArray? = null
    var persianDateAsString: String? = null

    override fun init() {
        persianDate = DateUtil.gregorianToJalali(
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH) + 1,
            calendar.get(Calendar.DAY_OF_MONTH)
        )

        persianDateAsString = persianDate?.joinToString(" - ")
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

    override fun firstDayPositionInWeek(): Int {
        val position = getDayOfWeek()
        return if (position == 7) 0 else position
    }

    override fun generateDays(): List<Int> {
        val days = ArrayList<Int>()

        val countOfDays = getJalaliMonthCount(
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH) + 1,
            calendar.get(Calendar.DAY_OF_MONTH)
        )

        //shift days
        repeat(firstDayPositionInWeek()) {
            days.add(-1)
        }

        for (day in 1..countOfDays) {
            days.add(day)
        }

        return days
    }

    override fun getYear(): Int = DateUtil.gregorianToJalali(
        calendar.get(Calendar.YEAR),
        calendar.get(Calendar.MONTH) + 1,
        calendar.get(Calendar.DAY_OF_MONTH)
    )[0]

    override fun getMonth(): Int = persianDate?.getOrNull(1) ?: -1

    override fun getMonthName(): String {
        return nameOfMonths.getOrNull(
            (persianDate?.getOrNull(1) ?: -1) - 1
        ) ?: "نا شناخته"
    }

    override fun set(field: Int, value: Int) {
        calendar.set(field, value)
    }

    override fun get(field: Int) = calendar.get(field)

    override fun clear() {
        //calendar.clear()
    }

    override fun getToday(): DayItem {
        val todayCalendar = Calendar.getInstance()
        val today = DateUtil.gregorianToJalali(
            todayCalendar.get(Calendar.YEAR),
            todayCalendar.get(Calendar.MONTH) + 1,
            todayCalendar.get(Calendar.DAY_OF_MONTH)
        )
        return DayItem(today[0], today[1], today[2], false)
    }

    override fun getNewInstanceOfCalendar(): BaseCalendar = MyJalaliCalendar()

    private fun getDayOfWeek(): Int {
        val jalali = DateUtil.gregorianToJalali(
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH) + 1,
            calendar.get(Calendar.DAY_OF_MONTH)
        )

        val gregorian = DateUtil.jalaliToGregorian(
            jalali[0],
            jalali[1],
            1,
        )

        val calendar = Calendar.getInstance().apply {
            set(Calendar.YEAR, gregorian[0])
            set(Calendar.MONTH, gregorian[1] - 1)
            set(Calendar.DAY_OF_MONTH, gregorian[2])
        }

        return calendar.get(Calendar.DAY_OF_WEEK)
    }

    private fun getJalaliMonthCount(year: Int, month: Int, day: Int): Int {
        val gregorianDayMonth =
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
        val jalaliMonth: Int = if (days < 186) {
            1 + (days / 31)
        } else {
            7 + ((days - 186) / 30)
        }

        return if (jalaliMonth <= 6) {
            31
        } else if (jalaliMonth in 7..11) {
            30
        } else {
            if (isLeapYear(jalaliYear))
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
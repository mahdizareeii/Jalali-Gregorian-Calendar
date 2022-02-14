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

        val countOfDays = DateUtil.getJalaliMonthCount(
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
}
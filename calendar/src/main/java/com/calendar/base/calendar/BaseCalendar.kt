package com.calendar.base.calendar

import com.calendar.base.model.MonthItem
import java.util.*

abstract class BaseCalendar {

    abstract val nameOfMonths: List<String>

    abstract fun getDisplayedMonthName(value: Int): String

    /** you must set month like this
     *  'calendar.set(Calendar.MONTH, month)'
     *  before call this method
     */
    abstract fun firstDayPositionInWeek(): Int
    abstract fun generateDays(month: Int): List<Int>
    abstract fun getYearName(): String
    abstract fun set(field: Int, value: Int)
    abstract fun get(field: Int): Int

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
                var temp = value
                var tempYear = todayYear
                while (temp >= 12) {
                    for (month in 0..11)
                        months.add(MonthItem(getNewInstanceOfCalendar(), month, tempYear))
                    temp -= 12
                    tempYear++
                }

                if (temp != 0)
                    for (month in 0..temp)
                        months.add(MonthItem(getNewInstanceOfCalendar(), month, tempYear))
            }
        }
        return months
    }

    protected abstract fun getNewInstanceOfCalendar(): BaseCalendar
}
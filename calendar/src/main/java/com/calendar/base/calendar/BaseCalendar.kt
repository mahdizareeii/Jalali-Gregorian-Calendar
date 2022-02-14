package com.calendar.base.calendar

import com.calendar.base.model.DayItem
import com.calendar.base.model.MonthItem
import java.util.*

abstract class BaseCalendar {

    abstract val nameOfMonths: List<String>
    abstract fun init()

    /** you must set month like this
     *  calendar.set(Calendar.MONTH, month)
     *  before call this method
     *
     *  @return position of day in week
     */
    abstract fun firstDayPositionInWeek(): Int

    /**
     * @return list of days of month
     */
    abstract fun generateDays(): List<Int>
    abstract fun getYear(): Int
    abstract fun getMonth(): Int
    abstract fun getMonthName(): String
    abstract fun set(field: Int, value: Int)
    abstract fun get(field: Int): Int
    abstract fun clear()
    abstract fun getToday(): DayItem
    protected abstract fun getNewInstanceOfCalendar(): BaseCalendar

    /**
     *  you can increase up month and year with set field and you will get next dates
     *  @param field the field that you want increase up
     *  @param value the value that increase up field
     *  @return a list that contain months
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
                        months.add(MonthItem(getNewInstanceOfCalendar(), month + 1, year))
                }
            else if (field == Calendar.MONTH) {
                var tempMonth = value
                var tempYear = todayYear
                while (tempMonth >= 12) {
                    for (month in 0..11)
                        months.add(MonthItem(getNewInstanceOfCalendar(), month + 1, tempYear))
                    tempMonth -= 12
                    tempYear++
                }

                if (tempMonth != 0)
                    for (month in 0..tempMonth)
                        months.add(MonthItem(getNewInstanceOfCalendar(), month + 1, tempYear))
            }
        }
        return months
    }
}
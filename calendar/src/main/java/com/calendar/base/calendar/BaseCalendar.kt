package com.calendar.base.calendar

import com.calendar.base.model.Day
import com.calendar.base.model.Month
import java.util.*

abstract class BaseCalendar {

    abstract val nameOfMonths: List<String>
    abstract fun set(field: Int, value: Int)
    abstract fun get(field: Int): Int
    abstract fun clear()
    abstract fun getYear(): Int
    abstract fun getMonth(): Int
    abstract fun getMonthName(): String
    protected abstract fun getNewInstanceOfCalendar(): BaseCalendar

    /** you must set month like this
     *  calendar.set(Calendar.MONTH, month)
     *  before call this method
     *
     *  @return position of day in week
     */
    abstract fun getFirstDayPositionInWeek(): Int

    abstract fun getToday(): Day

    /**
     *  you can increase up month and year with set field and you will get next dates
     *  @param field the field that you want increase up
     *  @param value the value that increase up field
     *  @return a list that contain months
     */
    abstract fun getNextDates(field: Int, value: Int): List<Month>

    /**
     * @return list of days of month
     */
    abstract fun generateDays(): List<Int>

    fun getMonthsBetweenDateRange(
        field: Int,
        value: Int,
        startMonth: Int,
        startYear: Int,
        nextMonth: Int,
        nextYear: Int
    ): List<Month> {
        val months = ArrayList<Month>()
        if (startYear == nextYear) {
            if (field == Calendar.MONTH)
                for (month in startMonth..nextMonth) {
                    months.add(Month(getNewInstanceOfCalendar(), month, startYear))
                }
        } else {
            if (field == Calendar.YEAR)
                for (year in startYear..nextYear) {
                    if (year == startYear)
                        for (month in startMonth..12) {
                            months.add(Month(getNewInstanceOfCalendar(), month, year))
                        }
                    else
                        for (month in 1..12) {
                            months.add(Month(getNewInstanceOfCalendar(), month, year))
                        }
                }
            else if (field == Calendar.MONTH) {
                var tempMonth = value
                var tempYear = startYear
                while (tempMonth >= 12) {
                    if (tempYear == startYear)
                        for (month in startMonth..12)
                            months.add(Month(getNewInstanceOfCalendar(), month, tempYear))
                    else
                        for (month in 1..12)
                            months.add(Month(getNewInstanceOfCalendar(), month, tempYear))
                    tempMonth -= 12
                    tempYear++
                }

                if (tempMonth != 0)
                    for (month in 1..tempMonth)
                        months.add(Month(getNewInstanceOfCalendar(), month, tempYear))
            }
        }
        return months
    }
}
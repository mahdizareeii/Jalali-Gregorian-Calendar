package com.calendar.model

import com.calendar.calendar.BaseCalendar
import com.calendar.calendar.MyGregorianCalendar
import java.util.*

/**
 * Gregorian date sample
 * @sample Month(gregorianInstance,2050,0)
 * Jalali date sample
 * @sample Month(jalaliInstance,1420,0)
 *
 * @param calendar hold the instance of calendar
 * @param year hold every year that you give
 * @param month hold every month that you give (must be start with 0)
 */
data class Month(
    private val calendar: BaseCalendar,
    private val month: Int,
    private val year: Int
) {
    private val days = ArrayList<Day>()

    var listener: MonthItemListener? = null

    init {
        calendar.clear()
        calendar.set(Calendar.YEAR, year)
        calendar.set(Calendar.MONTH, month - 1)
        calendar.set(Calendar.DAY_OF_MONTH, 1)
    }

    val getYear get() = calendar.getYear()
    val getMonth get() = calendar.getMonth()
    val getMonthName get() = calendar.getMonthName()

    /**
     * if you want to change shift day parameters
     * @see Day.isNotEmptyDay method and refactor that depend on your parameters
     */
    fun generateDays(customDays: List<Day>): List<Day> {
        if (days.isNullOrEmpty()) {
            days.addAll(
                calendar.generateDays().map {
                    if (it == -1) {
                        //shift days
                        Day(-1, -1, -1, false)
                    } else {
                        val day = Day(
                            year = calendar.getYear(),
                            month = calendar.getMonth() + 1,
                            day = it,
                            isGregorianDate = calendar is MyGregorianCalendar
                        )

                        customDays.firstOrNull { customDay -> customDay == day } ?: day
                    }
                }
            )
        }
        return days
    }
}
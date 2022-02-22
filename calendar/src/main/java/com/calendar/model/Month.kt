package com.calendar.model

import com.calendar.calendar.BaseCalendar
import com.calendar.calendar.MyGregorianCalendar
import java.util.*

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

    fun generateDays(customDays: List<Day>): List<Day> {
        if (days.isNullOrEmpty()) {
            days.addAll(
                calendar.generateDays().map {
                    if (it == -1) {
                        //shift days
                        Day(null, null, null, null)
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
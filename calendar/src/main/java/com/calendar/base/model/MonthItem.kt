package com.calendar.base.model

import com.calendar.base.calendar.BaseCalendar
import com.calendar.base.calendar.MyGregorianCalendar
import java.util.*

data class MonthItem(
    private val calendar: BaseCalendar,
    private val month: Int,
    private val year: Int
) {
    private val days = ArrayList<DayItem>()

    var listener: MonthItemListener? = null

    init {
        calendar.clear()
        calendar.set(Calendar.YEAR, year)
        calendar.set(Calendar.MONTH, month)
        calendar.init()
    }

    val getYear get() = calendar.getYear()
    val getMonth get() = calendar.getMonth()
    val getMonthName get() = calendar.getMonthName()

    fun generateDays(customDays: List<DayItem>): List<DayItem> {
        if (days.isNullOrEmpty()) {
            days.addAll(
                calendar.generateDays().map {
                    if (it == -1) {
                        //shift days
                        DayItem(null, null, null, null)
                    } else {
                        val day = DayItem(
                            year = calendar.getYear(),
                            month = calendar.getMonth(),
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
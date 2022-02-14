package com.calendar.base.model

import com.calendar.base.calendar.BaseCalendar
import com.calendar.base.calendar.MyGregorianCalendar
import com.calendar.base.calendar.MyJalaliCalendar
import java.util.*

data class MonthItem(
    val calendar: BaseCalendar,
    val month: Int,
    val year: Int
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
    val getMonthName get() = calendar.getMonthName()

    fun generateDays(customDays: List<DayItem>): List<DayItem> {
        if (days.isNullOrEmpty()) {
            days.addAll(
                calendar.generateDays().map {
                    if (it == -1) {
                        DayItem(null, null, null)
                    } else {
                        val day = DayItem(
                            year = calendar.getYear(),
                            month = calendar.getMonth(),
                            day = it
                        )

                        (customDays.firstOrNull { customDay -> customDay == day } ?: day).apply {
                            if (calendar is MyJalaliCalendar) {
                                val gregorianDate = calendar.jalaliToGregorian(
                                    day.year!!,
                                    day.month!!,
                                    day.day!!
                                )
                                setGregorianDate(
                                    year = gregorianDate[0],
                                    month = gregorianDate[1],
                                    day = gregorianDate[2]
                                )
                            }
                        }
                    }
                }
            )
        }
        return days
    }
}
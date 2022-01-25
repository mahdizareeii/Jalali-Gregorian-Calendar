package com.calendar.base.model

import android.util.Log
import com.calendar.base.calendar.BaseCalendar
import java.util.*
import kotlin.collections.ArrayList

data class MonthItem(
    val calendar: BaseCalendar,
    val month: Int
) {
    val days = ArrayList<DayItem>()
    fun getDayOfMonths(): List<DayItem> {
        if (days.isNullOrEmpty()) {
            calendar.set(Calendar.MONTH, month)
            val dayOfMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH)

            for (i in 1..dayOfMonth) {
                if (i == 1) {
                    calendar.set(Calendar.DAY_OF_MONTH, i)
                    val dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK)
                    Log.i("asdasd", "day of week: $dayOfWeek")
                    for (j in 1..dayOfWeek) {
                        days.add(DayItem(calendar, null))
                    }
                }
                days.add(DayItem(calendar, i))
            }
        }
        return days
    }

    fun getYear() = calendar.get(Calendar.YEAR)

    fun getDisplayedName(): String {
        return calendar.getDisplayedMonthName(month)
    }
}
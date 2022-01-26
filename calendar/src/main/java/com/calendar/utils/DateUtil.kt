package com.calendar.utils

import com.calendar.base.calendar.BaseCalendar
import com.calendar.base.model.MonthItem
import java.util.*
import kotlin.collections.ArrayList

object DateUtil {

    fun getNextDates(calendar: BaseCalendar, field: Int, value: Int): List<MonthItem> {
        val months = ArrayList<MonthItem>()
        val today = Calendar.getInstance()
        val next = Calendar.getInstance().apply {
            set(field, get(field) + value)
        }

        val todayMonth = today.get(Calendar.MONTH)
        val nextMonth = next.get(Calendar.MONTH)
        for (month in todayMonth until nextMonth) {
            months.add(MonthItem(calendar, month))
        }
        return months
    }

    fun convertTo() {

    }

}
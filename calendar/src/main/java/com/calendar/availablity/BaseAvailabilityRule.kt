package com.calendar.availablity

import com.calendar.CalendarProperties
import com.calendar.model.Day
import com.calendar.model.DayStatus

open class BaseAvailabilityRule(
    private val availableFromToday: Boolean
) {

    open fun isAvailable(
        currentDay: Day,
        properties: CalendarProperties
    ): Boolean {
        val fromToday = checkAvailabilityFromToday(currentDay, properties.getToday())
        return when (availableFromToday) {
            true -> fromToday && currentDay.status == DayStatus.AVAILABLE
            false -> currentDay.status == DayStatus.AVAILABLE
        }
    }

    fun checkAvailabilityFromToday(
        currentDay: Day,
        today: Day
    ) = currentDay >= today
}
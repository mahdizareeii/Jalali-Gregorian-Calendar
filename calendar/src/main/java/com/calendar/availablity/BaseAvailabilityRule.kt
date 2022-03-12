package com.calendar.availablity

import com.calendar.CalendarProperties
import com.calendar.model.Day

open class BaseAvailabilityRule(
    private val availableFromToday: Boolean
) {

    open fun isAvailable(
        currentDay: Day,
        properties: CalendarProperties
    ): Boolean {
        val fromToday = checkAvailabilityFromToday(currentDay, properties.getToday())
        return when (availableFromToday) {
            true -> fromToday && !currentDay.isDisable
            false -> !currentDay.isDisable
        }
    }

    fun checkAvailabilityFromToday(
        currentDay: Day,
        today: Day
    ) = currentDay >= today
}
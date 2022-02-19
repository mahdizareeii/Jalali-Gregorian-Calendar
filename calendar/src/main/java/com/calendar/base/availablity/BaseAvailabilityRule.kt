package com.calendar.base.availablity

import com.calendar.base.model.Day
import com.calendar.CalendarProperties

open class BaseAvailabilityRule(
    private val availableFromToday: Boolean,
    private val unAvailableDisableDays: Boolean
) {

    open fun isAvailable(
        currentDay: Day,
        properties: CalendarProperties
    ): Boolean {
        val fromToday =
            availableFromToday && checkAvailabilityFromToday(currentDay, properties.getToday())

        val disableDays = unAvailableDisableDays && currentDay.isDisable

        return if (availableFromToday && !unAvailableDisableDays) fromToday
        else if (unAvailableDisableDays && !availableFromToday) disableDays
        else if (availableFromToday && unAvailableDisableDays) fromToday && !disableDays
        else true
    }

    fun checkAvailabilityFromToday(
        currentDay: Day,
        today: Day
    ) = currentDay >= today
}
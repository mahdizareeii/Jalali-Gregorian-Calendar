package com.calendar.base.availablity

import com.calendar.base.model.DayItem
import com.calendar.base.types.CalendarProperties

open class BaseAvailabilityRule(
    private val availableFromToday: Boolean,
    private val unAvailableDisableDays: Boolean
) {

    open fun isAvailable(
        currentItem: DayItem,
        properties: CalendarProperties
    ): Boolean {
        val fromToday =
            availableFromToday && checkAvailabilityFromToday(currentItem, properties.getToday())

        val disableDays = unAvailableDisableDays && currentItem.isDisable

        return if (availableFromToday && !unAvailableDisableDays) fromToday
        else if (unAvailableDisableDays && !availableFromToday) disableDays
        else if (availableFromToday && unAvailableDisableDays) fromToday && !disableDays
        else true
    }

    fun checkAvailabilityFromToday(
        currentItem: DayItem,
        today: DayItem
    ) = currentItem >= today
}
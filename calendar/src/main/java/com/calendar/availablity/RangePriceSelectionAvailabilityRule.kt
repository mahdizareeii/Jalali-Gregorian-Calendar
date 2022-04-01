package com.calendar.availablity

import com.calendar.CalendarProperties
import com.calendar.model.Day
import com.calendar.model.DayStatus
import com.calendar.types.rangeslelection.RangeSelection
import com.calendar.utils.DateUtil

class RangePriceSelectionAvailabilityRule(
    availableFromToday: Boolean
) : BaseAvailabilityRule(availableFromToday) {

    private var firstUnAvailableItem: Day? = null

    override fun isAvailable(
        currentDay: Day,
        properties: CalendarProperties
    ): Boolean {
        val minDaysInRangeSelection = when (val type = properties.calendarType) {
            is RangeSelection -> type.minDaysInRangeSelection
            else -> 1
        }
        val checkBaseAvailability = checkAvailabilityFromToday(
            currentDay,
            properties.getToday()
        ) && getDiffDaysFromCheckIn(
            currentDay,
            properties,
            minDaysInRangeSelection
        ) >= minDaysInRangeSelection
        return when {
            properties.isCheckInSelect() -> {
                when {
                    //checkAvailability after current day
                    firstUnAvailableItem == null && currentDay >= properties.selectedCheckIn -> {
                        if (currentDay.status != DayStatus.AVAILABLE)
                            firstUnAvailableItem = currentDay
                        checkBaseAvailability
                    }
                    //return false when current item is bigger than firstUnAvailableItem
                    firstUnAvailableItem != null && currentDay > firstUnAvailableItem -> false
                    else -> currentDay >= properties.selectedCheckIn
                }
            }
            properties.isCheckOutSelect() -> {
                firstUnAvailableItem = null
                if (currentDay.status != DayStatus.AVAILABLE && currentDay == properties.selectedCheckOut) true
                else super.isAvailable(currentDay, properties)
            }
            else -> {
                firstUnAvailableItem = null
                super.isAvailable(currentDay, properties)
            }
        }
    }

    private fun getDiffDaysFromCheckIn(
        currentDay: Day,
        properties: CalendarProperties,
        minDaysInRangeSelection: Int
    ): Int {
        if (currentDay.isEmptyDay()) return minDaysInRangeSelection
        return when {
            currentDay > properties.selectedCheckIn -> DateUtil.diffDays(
                properties.selectedCheckIn,
                currentDay
            )[1].toInt()
            currentDay < properties.selectedCheckIn -> DateUtil.diffDays(
                currentDay,
                properties.selectedCheckIn
            )[1].toInt()
            else -> minDaysInRangeSelection
        }
    }

}
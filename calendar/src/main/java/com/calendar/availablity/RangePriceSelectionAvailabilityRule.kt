package com.calendar.availablity

import com.calendar.CalendarProperties
import com.calendar.model.Day
import com.calendar.model.DayStatus
import com.calendar.types.rangeslelection.RangeSelection
import com.calendar.utils.DateUtil

class RangePriceSelectionAvailabilityRule(
    availableFromToday: Boolean
) : BaseAvailabilityRule(availableFromToday) {

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
                if (!checkCurrentDayIsInCustomDays(currentDay, properties)) {
                    checkBaseAvailability && DateUtil.diffDays(
                        properties.customDays.lastOrNull(),
                        currentDay
                    )[1].toInt() == 1
                } else {
                    checkBaseAvailability
                            && checkAvailabilityDaysAfterCurrentDay(currentDay, properties)
                            && checkAvailabilityDaysBeforeCurrentDay(currentDay, properties)
                }
            }
            properties.isCheckOutSelect() -> {
                if (currentDay.status != DayStatus.AVAILABLE && currentDay == properties.selectedCheckOut) true
                else super.isAvailable(currentDay, properties)
            }
            else -> super.isAvailable(currentDay, properties)
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

    private fun checkCurrentDayIsInCustomDays(
        currentDay: Day,
        properties: CalendarProperties
    ) = properties.customDays.any { it == currentDay }

    private fun checkAvailabilityDaysAfterCurrentDay(
        currentDay: Day,
        properties: CalendarProperties
    ) = !properties.customDays.any {
        it < currentDay && properties.selectedCheckIn!! < it && it.status != DayStatus.AVAILABLE
    }

    private fun checkAvailabilityDaysBeforeCurrentDay(
        currentDay: Day,
        properties: CalendarProperties
    ) = !properties.customDays.any {
        it >= currentDay && properties.selectedCheckIn!! > it && it.status != DayStatus.AVAILABLE
    }
}
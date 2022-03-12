package com.calendar.availablity

import com.calendar.calendar.MyJalaliCalendar
import com.calendar.model.Day
import com.calendar.CalendarProperties
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
        return when {
            properties.isCheckInSelect() -> {
                checkAvailabilityFromToday(currentDay, properties.getToday()) &&
                        getDifDaysFromCheckIn(
                            currentDay,
                            properties,
                            minDaysInRangeSelection
                        ) >= minDaysInRangeSelection &&
                        !properties.customDays.any {
                            it < currentDay && properties.selectedCheckIn!! < it && it.isDisable
                        } &&
                        !properties.customDays.any {
                            it >= currentDay && properties.selectedCheckIn!! > it && it.isDisable
                        }
            }
            properties.isCheckOutSelect() -> {
                if (currentDay.isDisable && currentDay == properties.selectedCheckOut) true
                else super.isAvailable(currentDay, properties)
            }
            else -> super.isAvailable(currentDay, properties)
        }
    }

    private fun getDifDaysFromCheckIn(
        currentDay: Day,
        properties: CalendarProperties,
        minDaysInRangeSelection: Int
    ) =
        if (currentDay.isNotEmptyDay() && currentDay > properties.selectedCheckIn) {
            if (properties.regionalType.calendar is MyJalaliCalendar) {
                DateUtil.diffDaysJalali(
                    properties.selectedCheckIn,
                    currentDay
                )[1].toInt()
            } else {
                DateUtil.diffDaysGregorian(
                    properties.selectedCheckIn,
                    currentDay,
                )[1].toInt()
            }
        } else if (currentDay.isNotEmptyDay() && currentDay < properties.selectedCheckIn)
            if (properties.regionalType.calendar is MyJalaliCalendar) {
                DateUtil.diffDaysJalali(
                    currentDay,
                    properties.selectedCheckIn
                )[1].toInt()
            } else {
                DateUtil.diffDaysGregorian(
                    currentDay,
                    properties.selectedCheckIn
                )[1].toInt()
            }
        else {
            minDaysInRangeSelection
        }
}
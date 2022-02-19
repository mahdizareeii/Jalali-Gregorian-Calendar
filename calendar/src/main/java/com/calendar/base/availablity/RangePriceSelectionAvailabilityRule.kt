package com.calendar.base.availablity

import com.calendar.base.calendar.MyJalaliCalendar
import com.calendar.base.model.Day
import com.calendar.CalendarProperties
import com.calendar.utils.DateUtil

class RangePriceSelectionAvailabilityRule(
    availableFromToday: Boolean,
    unAvailableDisableDays: Boolean
) : BaseAvailabilityRule(availableFromToday, unAvailableDisableDays) {

    override fun isAvailable(
        currentDay: Day,
        properties: CalendarProperties
    ): Boolean {
        return when {
            properties.isCheckInSelect() -> {
                checkAvailabilityFromToday(currentDay, properties.getToday()) &&
                        getDifDaysFromCheckIn(
                            currentDay,
                            properties
                        ) >= properties.minDaysInRangeSelection &&
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

    private fun getDifDaysFromCheckIn(currentDay: Day, properties: CalendarProperties) =
        if (currentDay.isNotNull() && currentDay > properties.selectedCheckIn) {
            if (properties.regionalType.calendar is MyJalaliCalendar) {
                DateUtil.diffDaysJalali(
                    properties.selectedCheckIn,
                    currentDay
                )
            } else {
                DateUtil.diffDaysGregorian(
                    properties.selectedCheckIn,
                    currentDay,
                )
            }
        } else if (currentDay.isNotNull() && currentDay < properties.selectedCheckIn)
            if (properties.regionalType.calendar is MyJalaliCalendar) {
                DateUtil.diffDaysJalali(
                    currentDay,
                    properties.selectedCheckIn
                )
            } else {
                DateUtil.diffDaysGregorian(
                    currentDay,
                    properties.selectedCheckIn
                )
            }
        else {
            properties.minDaysInRangeSelection
        }
}
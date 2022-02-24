package com.calendar.availablity

import com.calendar.calendar.MyJalaliCalendar
import com.calendar.model.Day
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
            properties.minDaysInRangeSelection
        }
}
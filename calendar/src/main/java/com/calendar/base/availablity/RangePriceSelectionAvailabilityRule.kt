package com.calendar.base.availablity

import com.calendar.base.calendar.MyJalaliCalendar
import com.calendar.base.model.DayItem
import com.calendar.base.types.CalendarProperties
import com.calendar.utils.DateUtil

class RangePriceSelectionAvailabilityRule(
    availableFromToday: Boolean,
    unAvailableDisableDays: Boolean
) : BaseAvailabilityRule(availableFromToday, unAvailableDisableDays) {

    override fun isAvailable(
        currentItem: DayItem,
        properties: CalendarProperties
    ): Boolean {
        return when {
            properties.isCheckInSelect() -> {
                checkAvailabilityFromToday(currentItem, properties.getToday()) &&
                        getDifDaysFromCheckIn(
                            currentItem,
                            properties
                        ) >= properties.minDaysInRangeSelection &&
                        !properties.customDays.any {
                            it < currentItem && properties.selectedCheckIn!! < it && it.isDisable
                        } &&
                        !properties.customDays.any {
                            it >= currentItem && properties.selectedCheckIn!! > it && it.isDisable
                        }
            }
            properties.isCheckOutSelect() -> {
                if (currentItem.isDisable && currentItem == properties.selectedCheckOut) true
                else super.isAvailable(currentItem, properties)
            }
            else -> super.isAvailable(currentItem, properties)
        }
    }

    private fun getDifDaysFromCheckIn(currentItem: DayItem, properties: CalendarProperties) =
        if (currentItem.isNotNull() && currentItem > properties.selectedCheckIn) {
            if (properties.regionalType.calendar is MyJalaliCalendar) {
                DateUtil.diffDaysJalali(
                    properties.selectedCheckIn,
                    currentItem
                )
            } else {
                DateUtil.diffDaysGregorian(
                    properties.selectedCheckIn,
                    currentItem,
                )
            }
        } else if (currentItem.isNotNull() && currentItem < properties.selectedCheckIn)
            if (properties.regionalType.calendar is MyJalaliCalendar) {
                DateUtil.diffDaysJalali(
                    currentItem,
                    properties.selectedCheckIn
                )
            } else {
                DateUtil.diffDaysGregorian(
                    currentItem,
                    properties.selectedCheckIn
                )
            }
        else {
            properties.minDaysInRangeSelection
        }
}
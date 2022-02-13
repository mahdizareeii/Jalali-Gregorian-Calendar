package com.calendar.base.types

import androidx.recyclerview.widget.RecyclerView
import com.calendar.base.calendar.MyJalaliCalendar
import com.calendar.base.calendar.RegionalType
import com.calendar.base.model.DayItem
import com.calendar.utils.DateUtil

data class CalendarProperties(
    val regionalType: RegionalType,
    val calendarType: CalendarType,
    val calendarOrientation: Int,
    val showDaysPrice: Boolean,
    val minDaysInRangeSelection: Int = 1,
    private val availableFromToday: Boolean,

    //for set custom days pricing and etc
    var customDays: ArrayList<DayItem> = ArrayList(),

    //for range selection
    var selectedCheckIn: DayItem? = null,
    var selectedCheckOut: DayItem? = null,

    //for multiple selection
    val selectedMultipleDayItem: ArrayList<DayItem> = ArrayList(),

    //for single selection
    var selectedSingle: DayItem? = null

    //for without selection
) {
    internal fun txtPriceVisibility(currentItem: DayItem) =
        showDaysPrice && selectedCheckOut != currentItem

    internal fun calendarIsReverse() =
        regionalType == RegionalType.Jalali && calendarOrientation == HORIZONTAL

    internal fun isCheckInSelect() = selectedCheckIn != null && selectedCheckOut == null
    internal fun isCheckOutSelect() = selectedCheckIn != null && selectedCheckOut != null

    internal fun getRangeSelectionAvailability(currentItem: DayItem) = when {
        isCheckInSelect() -> {
            checkAvailabilityFromToday(currentItem) &&
                    getDifDaysFromCheckIn(currentItem) >= minDaysInRangeSelection &&
                    !customDays.any {
                        it < currentItem && selectedCheckIn!! < it && it.isDisable
                    } &&
                    !customDays.any {
                        it >= currentItem && selectedCheckIn!! > it && it.isDisable
                    }
        }
        isCheckOutSelect() -> {
            if (currentItem.isDisable && currentItem == selectedCheckOut) true
            else checkAvailableFromTodayAndDaysNotDisable(currentItem)
        }
        else -> checkAvailableFromTodayAndDaysNotDisable(currentItem)
    }

    internal fun checkAvailableFromTodayAndDaysNotDisable(currentItem: DayItem) =
        checkAvailabilityFromToday(currentItem) && !currentItem.isDisable

    private fun checkAvailabilityFromToday(
        currentItem: DayItem
    ) = if (getToday() == null) true else currentItem >= getToday()!!

    private fun getDifDaysFromCheckIn(currentItem: DayItem) =
        if (currentItem.isNotNull() && currentItem > selectedCheckIn) {
            if (regionalType.calendar is MyJalaliCalendar) {
                DateUtil.diffDaysJalali(
                    selectedCheckIn,
                    currentItem
                )
            } else {
                DateUtil.diffDaysGregorian(
                    selectedCheckIn,
                    currentItem,
                )
            }
        } else if (currentItem.isNotNull() && currentItem < selectedCheckIn)
            if (regionalType.calendar is MyJalaliCalendar) {
                DateUtil.diffDaysJalali(
                    currentItem,
                    selectedCheckIn
                )
            } else {
                DateUtil.diffDaysGregorian(
                    currentItem,
                    selectedCheckIn
                )
            }
        else {
            minDaysInRangeSelection
        }

    private fun getToday(): DayItem? =
        if (availableFromToday) regionalType.calendar.getToday() else null

    companion object {
        const val HORIZONTAL = RecyclerView.HORIZONTAL
        const val VERTICAL = RecyclerView.VERTICAL
    }
}
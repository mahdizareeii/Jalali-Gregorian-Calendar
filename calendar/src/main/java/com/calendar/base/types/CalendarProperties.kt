package com.calendar.base.types

import androidx.recyclerview.widget.RecyclerView
import com.calendar.base.availablity.BaseAvailability
import com.calendar.base.calendar.RegionalType
import com.calendar.base.model.DayItem

data class CalendarProperties(
    val regionalType: RegionalType,
    val calendarType: CalendarType,
    val calendarOrientation: Int,
    val showDaysPrice: Boolean,
    val minDaysInRangeSelection: Int = 1,
    val availability: BaseAvailability,
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

    fun getToday(): DayItem = regionalType.calendar.getToday()

    companion object {
        const val HORIZONTAL = RecyclerView.HORIZONTAL
        const val VERTICAL = RecyclerView.VERTICAL
    }
}
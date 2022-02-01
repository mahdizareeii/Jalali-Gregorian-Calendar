package com.calendar.base.types

import androidx.recyclerview.widget.RecyclerView
import com.calendar.base.calendar.RegionalType
import com.calendar.base.model.DayItem

data class CalendarProperties(
    val regionalType: RegionalType,
    val calendarType: CalendarType,
    val calendarOrientation: Int,
    private val availableFromToday: Boolean,

    //for range selection
    var selectedCheckIn: DayItem? = null,
    var selectedCheckOut: DayItem? = null,

    //for multiple selection
    val selectedMultipleDayItem: ArrayList<DayItem> = ArrayList(),

    //for single selection
    var selectedSingle: DayItem? = null

    //for without selection
) {
    fun getToday(): DayItem? = if (availableFromToday) regionalType.calendar.getToday() else null
    fun calendarIsReverse() =
        regionalType == RegionalType.Jalali && calendarOrientation == HORIZONTAL

    companion object {
        const val HORIZONTAL = RecyclerView.HORIZONTAL
        const val VERTICAL = RecyclerView.VERTICAL
    }
}
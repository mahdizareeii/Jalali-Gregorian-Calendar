package com.calendar.base.model

import android.graphics.Color

data class AgendaDays(
    val id: String? = null,
    val title: String? = null,
    val color: String? = null,
    val daysRangeList: List<AgendaDaysRange> = listOf(),
    val daysList: List<DayItem> = listOf()
) {
    data class AgendaDaysRange(
        val startDate: DayItem,
        val endDate: DayItem
    )

    fun getAgendaColor() = try {
        Color.parseColor(color)
    } catch (e: IllegalArgumentException) {
        Color.parseColor("#ffa500")
    }
}

package com.calendar.base.model

import android.view.View

data class DayItem(
    val year: Int?,
    val month: Int?,
    val day: Int?
) {
    val visibility = if (day != null) View.VISIBLE else View.INVISIBLE

    //TODO maybe return yyyy-MM-dd

    override fun toString(): String {
        return "$year - $month - $day"
    }
}
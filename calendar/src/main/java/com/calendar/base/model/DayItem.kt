package com.calendar.base.model

import android.view.View

data class DayItem(
    val day: Int?
) {
    val visibility = if (day != null) View.VISIBLE else View.INVISIBLE
}
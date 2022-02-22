package com.calendar.base.types.singleselection

import com.calendar.base.model.DayItem

interface SingleSelectionListener {
    fun onDaySelected(dayItem: DayItem?)
}
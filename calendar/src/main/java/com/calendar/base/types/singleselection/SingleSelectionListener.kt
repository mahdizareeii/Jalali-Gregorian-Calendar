package com.calendar.base.types.singleselection

import com.calendar.base.model.Day

interface SingleSelectionListener {
    fun onDaySelected(day: Day?)
}
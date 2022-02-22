package com.calendar.types.singleselection

import com.calendar.model.Day

interface SingleSelectionListener {
    fun onDaySelected(day: Day?)
}
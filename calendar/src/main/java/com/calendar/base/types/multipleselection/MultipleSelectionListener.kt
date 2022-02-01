package com.calendar.base.types.multipleselection

import com.calendar.base.model.DayItem

interface MultipleSelectionListener {
    fun onSelectedDays(selectedDays: List<DayItem>)
}
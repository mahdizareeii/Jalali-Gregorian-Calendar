package com.calendar.base.types.multipleselection

import com.calendar.base.model.Day

interface MultipleSelectionListener {
    fun onSelectedDays(selectedDays: List<Day>)
}
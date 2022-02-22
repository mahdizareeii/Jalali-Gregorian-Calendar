package com.calendar.types.multipleselection

import com.calendar.model.Day

interface MultipleSelectionListener {
    fun onSelectedDays(selectedDays: List<Day>)
}
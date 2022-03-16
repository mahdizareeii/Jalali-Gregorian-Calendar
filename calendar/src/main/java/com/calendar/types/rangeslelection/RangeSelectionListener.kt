package com.calendar.types.rangeslelection

import com.calendar.model.Day

interface RangeSelectionListener{
    fun onCheckInSelected(day: Day)
    fun onCheckOutSelected(day: Day)
    fun onSelectsRemoved()
}
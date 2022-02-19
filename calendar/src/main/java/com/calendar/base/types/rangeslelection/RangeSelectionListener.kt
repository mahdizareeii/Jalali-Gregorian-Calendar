package com.calendar.base.types.rangeslelection

import com.calendar.base.model.Day

interface RangeSelectionListener{
    fun onCheckInSelected(day: Day)
    fun onCheckOutSelected(day: Day)
}
package com.calendar.base.types.rangeslelection

import com.calendar.base.model.DayItem

interface RangeSelectionListener{
    fun onCheckInSelected(dayItem: DayItem)
    fun onCheckOutSelected(dayItem: DayItem)
}
package com.calendar.base.types.rangeslelection

interface RangeSelectionListener{
    fun onDayNotifyDataSetChangedFrom(position: Int)
    fun onDayNotifyDataSetChangedUntil(position: Int)
}
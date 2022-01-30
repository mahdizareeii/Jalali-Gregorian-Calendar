package com.calendar.base.adapter.day

interface DaysListener {
    fun onDayNotifyDataSetChangedFrom(position: Int)
    fun onDayNotifyDataSetChangedUntil(position: Int)
}
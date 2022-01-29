package com.calendar.base

import com.calendar.base.adapter.day.types.DaySelectionType
import com.calendar.base.adapter.day.types.multipleselection.MultipleSelection
import com.calendar.base.adapter.day.types.rangeslelection.RangeSelection
import com.calendar.base.adapter.day.types.singleselection.SingleSelection
import com.calendar.base.adapter.day.types.withoutselection.WithoutSelection

enum class CalendarSelectionType(internal val daySelectionType: DaySelectionType) {
    MULTIPLE_SELECTION(MultipleSelection()),
    RANGE_SELECTION(RangeSelection()),
    SINGLE_SELECTION(SingleSelection()),
    WITHOUT_SELECTION(WithoutSelection())
}
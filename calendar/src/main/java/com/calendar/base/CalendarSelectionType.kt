package com.calendar.base

import com.calendar.base.types.DaySelectionType
import com.calendar.base.types.multipleselection.MultipleSelection
import com.calendar.base.types.rangeslelection.RangeSelection
import com.calendar.base.types.singleselection.SingleSelection
import com.calendar.base.types.withoutselection.WithoutSelection

enum class CalendarSelectionType(internal val daySelectionType: DaySelectionType) {
    MULTIPLE_SELECTION(MultipleSelection()),
    RANGE_SELECTION(RangeSelection()),
    SINGLE_SELECTION(SingleSelection()),
    WITHOUT_SELECTION(WithoutSelection())
}
package com.calendar.base.adapter.day

import com.calendar.base.types.multipleselection.MultipleSelectionListener
import com.calendar.base.types.rangeslelection.RangeSelectionListener
import com.calendar.base.types.singleselection.SingleSelectionListener
import com.calendar.base.types.withoutselection.WithoutSelectionListener

interface DaysListener :
    RangeSelectionListener,
    MultipleSelectionListener,
    SingleSelectionListener,
    WithoutSelectionListener
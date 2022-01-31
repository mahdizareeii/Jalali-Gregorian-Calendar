package com.calendar

import android.content.Context
import android.util.AttributeSet
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.calendar.base.CalendarRegional
import com.calendar.base.adapter.month.MonthAdapter
import com.calendar.base.calendar.BaseCalendar
import com.calendar.base.types.CalendarType
import com.calendar.base.types.DaySelectionProperties

class CalendarView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : LinearLayout(context, attrs, defStyle) {

    private lateinit var calendar: BaseCalendar
    private lateinit var adapter: MonthAdapter
    private var recyclerView: RecyclerView

    init {
        inflate(context, R.layout.calendar, this)
        recyclerView = this.findViewById(R.id.rv_months)
    }

    fun submitNextDates(field: Int, value: Int) {
        adapter.submitList(calendar.getNextDates(field, value))
    }

    fun initCalendar(
        calendarRegional: CalendarRegional,
        calendarType: CalendarType,
        orientation: Int
    ) {
        calendar = calendarRegional.calendar

        val properties = DaySelectionProperties()

        adapter = MonthAdapter(calendarType, properties)

        if (orientation == LinearLayoutManager.HORIZONTAL)
            PagerSnapHelper().attachToRecyclerView(recyclerView)

        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(
            context,
            orientation,
            calendarRegional == CalendarRegional.Jalali
        )
    }
}
package com.calendar.base.adapter.day

import android.view.View
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.calendar.R
import com.calendar.base.model.DayItem
import com.calendar.base.types.DaySelectionProperties
import com.calendar.base.types.CalendarType
import com.calendar.utils.SquareView

class DayViewHolder(
    view: View,
    private val daySelectionProperties: DaySelectionProperties,
    private val calendarType: CalendarType,
    private val listener: DaysListener
) : RecyclerView.ViewHolder(view) {
    val bgDay: SquareView = view.findViewById(R.id.bg_day)
    val txtDay: AppCompatTextView = view.findViewById(R.id.txt_day)

    fun bind(dayItem: DayItem) {
        calendarType.bind(
            this,
            dayItem,
            daySelectionProperties,
            listener
        )
    }
}
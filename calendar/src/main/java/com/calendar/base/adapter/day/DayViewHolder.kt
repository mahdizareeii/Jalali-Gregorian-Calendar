package com.calendar.base.adapter.day

import android.view.View
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.calendar.R
import com.calendar.base.model.DayItem
import com.calendar.CalendarProperties
import com.calendar.utils.SquareView

internal class DayViewHolder(
    view: View,
    private val calendarProperties: CalendarProperties,
    private val listener: DaysAdapterListener
) : RecyclerView.ViewHolder(view) {
    val bgDay: SquareView = view.findViewById(R.id.bg_day)
    val txtDay: AppCompatTextView = view.findViewById(R.id.txt_day)
    val txtPrice: AppCompatTextView = view.findViewById(R.id.txt_price)

    fun bind(dayItem: DayItem) {
        calendarProperties.calendarType.bind(
            this,
            dayItem,
            calendarProperties,
            listener
        )
    }
}
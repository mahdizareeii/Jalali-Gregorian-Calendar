package com.calendar.base.adapter.day.viewholder

import android.content.Context
import android.view.View
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.calendar.CalendarProperties
import com.calendar.R
import com.calendar.base.adapter.day.DaysAdapterListener
import com.calendar.base.model.Day
import com.calendar.utils.SquareView

internal open class DayViewHolder(
    view: View,
    protected val properties: CalendarProperties,
    protected val listener: DaysAdapterListener
) : RecyclerView.ViewHolder(view) {
    val context: Context = view.context
    val bgDay: SquareView = view.findViewById(R.id.bg_day)
    val txtDay: AppCompatTextView = view.findViewById(R.id.txt_day)
    val txtPrice: AppCompatTextView = view.findViewById(R.id.txt_price)
    val imgStartAgenda: AppCompatImageView = view.findViewById(R.id.img_start_month_agenda)
    val imgEndAgenda: AppCompatImageView = view.findViewById(R.id.img_end_month_agenda)

    open fun bind(day: Day) {
        bgDay.visibility = day.dayVisibility
        textColor(this, day)
        txtDay.text = day.day.toString()
        bgDay.setOnClickListener {
            properties.calendarType.onDayClickListener.invoke(this, day, properties, listener)
        }
        properties.calendarType.bind(
            this,
            day,
            properties,
            listener
        )
    }

    private fun textColor(viewHolder: DayViewHolder, currentDay: Day) {
        val color = ContextCompat.getColor(
            context,
            when {
                currentDay.isHoliday -> R.color.red
                else -> {
                    if (properties.calendarType.isDaySelected(
                            currentDay,
                            properties
                        )
                    ) R.color.white
                    else R.color.secondary
                }
            }
        )
        viewHolder.txtDay.setTextColor(color)
        viewHolder.txtPrice.setTextColor(color)
    }
}
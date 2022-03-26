package com.calendar.adapter.day.viewholder

import android.content.Context
import android.view.View
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.calendar.CalendarProperties
import com.calendar.R
import com.calendar.adapter.day.DaysAdapterListener
import com.calendar.model.Day
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

    open fun bind(day: Day, position: Int) {
        this.itemView.visibility = dayVisibility(day.day)
        textColor(day, position)
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

    private fun dayVisibility(day: Int) = if (day != -1) View.VISIBLE else View.INVISIBLE

    private fun textColor(currentDay: Day, position: Int) {
        val color = ContextCompat.getColor(
            context,
            when {
                position == 6 -> R.color.red
                position == 13 -> R.color.red
                position == 20 -> R.color.red
                position == 27 -> R.color.red
                position == 34 -> R.color.red
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
        txtDay.setTextColor(color)
        txtPrice.setTextColor(color)
    }
}
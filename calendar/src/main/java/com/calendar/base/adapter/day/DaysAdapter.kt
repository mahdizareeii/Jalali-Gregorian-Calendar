package com.calendar.base.adapter.day

import android.content.Context
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.updateLayoutParams
import androidx.recyclerview.widget.RecyclerView
import com.calendar.R
import com.calendar.base.model.DayItem
import com.calendar.base.types.CalendarListener
import com.calendar.base.types.CalendarProperties
import com.google.android.flexbox.FlexboxLayoutManager

internal class DaysAdapter(
    private val calendarProperties: CalendarProperties,
    private val calendarListener: CalendarListener
) : RecyclerView.Adapter<DayViewHolder>() {

    private val dayList = ArrayList<DayItem>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DayViewHolder {
        return DayViewHolder(
            view = LayoutInflater.from(parent.context).inflate(
                R.layout.item_calendar_day,
                parent,
                false
            ).apply {
                updateLayoutParams<FlexboxLayoutManager.LayoutParams> {
                    width = (parent.measuredWidth / 7) - dp(parent.context, 2)
                    setMargins(
                        dp(parent.context, 1),
                        dp(parent.context, 1),
                        dp(parent.context, 1),
                        dp(parent.context, 1)
                    )
                }
            },
            calendarProperties = calendarProperties,
            listener = calendarListener
        )
    }

    override fun onBindViewHolder(holder: DayViewHolder, position: Int) {
        holder.bind(dayList[position])
    }

    private fun dp(context: Context, value: Int): Int =
        TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            value.toFloat(),
            context.resources.displayMetrics
        ).toInt()

    fun submitList(list: List<DayItem>) {
        dayList.clear()
        dayList.addAll(list)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = dayList.size
}
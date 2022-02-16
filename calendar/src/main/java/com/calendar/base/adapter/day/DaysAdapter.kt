package com.calendar.base.adapter.day

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.updateLayoutParams
import androidx.recyclerview.widget.RecyclerView
import com.calendar.CalendarProperties
import com.calendar.R
import com.calendar.base.model.DayItem
import com.calendar.utils.dp
import com.google.android.flexbox.FlexboxLayoutManager

internal class DaysAdapter(
    private val calendarProperties: CalendarProperties,
    private val daysAdapterListener: DaysAdapterListener
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
                    width = (parent.measuredWidth / 7) - dp(2)
                    setMargins(
                        dp(1),
                        dp(1),
                        dp(1),
                        dp(1)
                    )
                }
            },
            calendarProperties = calendarProperties,
            listener = daysAdapterListener
        )
    }

    override fun onBindViewHolder(holder: DayViewHolder, position: Int) {
        holder.bind(dayList[position])
    }

    override fun getItemCount(): Int = dayList.size

    fun submitList(list: List<DayItem>) {
        dayList.clear()
        dayList.addAll(list)
        notifyDataSetChanged()
    }
}
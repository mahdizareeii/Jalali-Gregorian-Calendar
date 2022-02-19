package com.calendar.base.adapter.day

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.updateLayoutParams
import androidx.recyclerview.widget.RecyclerView
import com.calendar.CalendarProperties
import com.calendar.R
import com.calendar.base.adapter.day.viewholder.AgendaDaysPriceViewHolder
import com.calendar.base.adapter.day.viewholder.AgendaRangeDaysViewHolder
import com.calendar.base.adapter.day.viewholder.DayPriceViewHolder
import com.calendar.base.adapter.day.viewholder.DayViewHolder
import com.calendar.base.model.Day
import com.calendar.utils.dp
import com.google.android.flexbox.FlexboxLayoutManager

internal class DaysAdapter(
    private val properties: CalendarProperties,
    private val daysAdapterListener: DaysAdapterListener
) : RecyclerView.Adapter<DayViewHolder>() {

    private val dayList = ArrayList<Day>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DayViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
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
        }

        return when {
            properties.showDaysPrice -> DayPriceViewHolder(
                view = view,
                properties = properties,
                listener = daysAdapterListener
            )
            properties.isAgendaDays && properties.showDaysPrice -> AgendaDaysPriceViewHolder(
                view = view,
                properties = properties,
                listener = daysAdapterListener
            )
            properties.isAgendaRangeDays -> AgendaRangeDaysViewHolder(
                view = view,
                properties = properties,
                listener = daysAdapterListener
            )
            else -> DayViewHolder(
                view = view,
                properties = properties,
                listener = daysAdapterListener
            )
        }
    }

    override fun onBindViewHolder(holder: DayViewHolder, position: Int) {
        holder.bind(dayList[position])
    }

    override fun getItemCount(): Int = dayList.size

    fun submitList(list: List<Day>) {
        dayList.clear()
        dayList.addAll(list)
        notifyDataSetChanged()
    }
}
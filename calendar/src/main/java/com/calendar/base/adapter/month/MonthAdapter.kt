package com.calendar.base.adapter.month

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.calendar.R
import com.calendar.base.model.MonthItem
import com.calendar.base.adapter.day.DaysAdapterListener
import com.calendar.CalendarProperties

internal class MonthAdapter(
    private val calendarProperties: CalendarProperties,
    private val listener: MonthAdapterListener
) : RecyclerView.Adapter<MonthViewHolder>(), DaysAdapterListener {

    private val monthList = ArrayList<MonthItem>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MonthViewHolder {
        return MonthViewHolder(
            view = LayoutInflater.from(parent.context).inflate(
                if (calendarProperties.calendarOrientation == CalendarProperties.VERTICAL)
                    R.layout.item_month_vertical
                else
                    R.layout.item_month_horizontal,
                parent,
                false
            ),
            calendarProperties = calendarProperties,
            daysAdapterListener = this
        )
    }

    override fun onBindViewHolder(holder: MonthViewHolder, position: Int) {
        holder.bind(monthList.size, position, monthList[position], listener)
    }

    override fun onDaysNotifyDataSetChanged() {
        for (indic in monthList.indices)
            monthList.getOrNull(indic)?.listener?.onDataSetChanged()
    }

    override fun getItemCount(): Int = monthList.size

    fun submitList(list: List<MonthItem>) {
        monthList.clear()
        monthList.addAll(list)
        notifyDataSetChanged()
    }
}
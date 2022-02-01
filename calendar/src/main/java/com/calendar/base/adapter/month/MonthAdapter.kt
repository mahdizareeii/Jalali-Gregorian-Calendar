package com.calendar.base.adapter.month

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.calendar.R
import com.calendar.base.model.MonthItem
import com.calendar.base.types.CalendarListener
import com.calendar.base.types.CalendarProperties

internal class MonthAdapter(
    private val calendarProperties: CalendarProperties
) : RecyclerView.Adapter<MonthViewHolder>(), CalendarListener {

    private val monthList = ArrayList<MonthItem>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MonthViewHolder {
        return MonthViewHolder(
            view = LayoutInflater.from(parent.context).inflate(
                R.layout.item_month,
                parent,
                false
            ),
            calendarProperties = calendarProperties,
            calendarListener = this
        )
    }

    override fun onBindViewHolder(holder: MonthViewHolder, position: Int) {
        holder.bind(monthList[position])
    }

    override fun onNotifyDataSetChanged() {
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
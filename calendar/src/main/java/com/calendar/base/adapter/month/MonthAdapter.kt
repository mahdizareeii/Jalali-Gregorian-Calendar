package com.calendar.base.adapter.month

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.calendar.R
import com.calendar.base.model.DayItem
import com.calendar.base.model.MonthItem

class MonthAdapter : RecyclerView.Adapter<MonthViewHolder>() {
    private val months = ArrayList<MonthItem>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MonthViewHolder {
        return MonthViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_month,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MonthViewHolder, position: Int) {
        holder.bind(months[position])
    }

    override fun getItemCount(): Int {
        return months.size
    }

    fun submitItem(months: List<MonthItem>) {
        this.months.clear()
        this.months.addAll(months)
        notifyDataSetChanged()
    }
}
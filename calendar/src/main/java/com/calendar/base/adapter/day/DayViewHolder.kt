package com.calendar.base.adapter.day

import android.view.View
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.calendar.R
import com.calendar.base.model.DayItem

class DayViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    private val day: AppCompatTextView = view.findViewById(R.id.txt_day)

    fun bind(dayItem: DayItem) {
        day.text = dayItem.day.toString()
    }
}
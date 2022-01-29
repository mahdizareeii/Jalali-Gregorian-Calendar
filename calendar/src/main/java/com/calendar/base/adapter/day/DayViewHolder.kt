package com.calendar.base.adapter.day

import android.view.View
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.calendar.R
import com.calendar.base.model.DayItem
import com.calendar.view.SquareView

class DayViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    private val bgDay: SquareView = view.findViewById(R.id.bg_day)
    private val txtDay: AppCompatTextView = view.findViewById(R.id.txt_day)

    fun bind(dayItem: DayItem) {
        bgDay.visibility = dayItem.visibility
        txtDay.text = dayItem.day.toString()
    }
}
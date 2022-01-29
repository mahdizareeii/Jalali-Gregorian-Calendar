package com.calendar.base.types.rangeslelection

import android.widget.Toast
import com.calendar.base.adapter.day.DayViewHolder
import com.calendar.base.model.DayItem
import com.calendar.base.types.DaySelectionType

internal class RangeSelection : DaySelectionType() {
    override fun bind(viewHolder: DayViewHolder, dayItem: DayItem) {
        super.bind(viewHolder, dayItem)
        viewHolder.bgDay.setOnClickListener {
            Toast.makeText(viewHolder.itemView.context, dayItem.toString(), Toast.LENGTH_SHORT)
                .show()
        }
    }
}
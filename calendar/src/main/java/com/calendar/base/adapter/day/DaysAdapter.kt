package com.calendar.base.adapter.day

import android.content.Context
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.updateLayoutParams
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.calendar.R
import com.calendar.base.model.DayItem
import com.google.android.flexbox.FlexboxLayoutManager

class DaysAdapter : ListAdapter<DayItem, DayViewHolder>(DiffUtilCallBack()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DayViewHolder {
        return DayViewHolder(
            LayoutInflater.from(parent.context).inflate(
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
            }
        )
    }

    override fun onBindViewHolder(holder: DayViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    private fun dp(context: Context, value: Int): Int =
        TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            value.toFloat(),
            context.resources.displayMetrics
        ).toInt()

    private class DiffUtilCallBack : DiffUtil.ItemCallback<DayItem>() {
        override fun areItemsTheSame(oldItem: DayItem, newItem: DayItem): Boolean {
            return oldItem.day == oldItem.day
        }

        override fun areContentsTheSame(oldItem: DayItem, newItem: DayItem): Boolean {
            return oldItem == newItem
        }
    }
}
package com.calendar.base.adapter.day

import android.content.Context
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.updateLayoutParams
import androidx.recyclerview.widget.RecyclerView
import com.calendar.R
import com.calendar.base.model.DayItem
import com.google.android.flexbox.FlexboxLayoutManager

class DaysAdapter : RecyclerView.Adapter<DayViewHolder>() {
    private val days = ArrayList<DayItem>()

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
        holder.bind(days[position])
    }

    override fun getItemCount(): Int = days.size

    fun submitItem(days: List<DayItem>) {
        this.days.clear()
        this.days.addAll(days)
        notifyDataSetChanged()
    }

    private fun dp(context: Context, value: Int): Int =
        TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            value.toFloat(),
            context.resources.displayMetrics
        ).toInt()
}
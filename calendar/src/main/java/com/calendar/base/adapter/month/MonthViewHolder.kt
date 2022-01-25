package com.calendar.base.adapter.month

import android.view.View
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.calendar.R
import com.calendar.base.adapter.day.DaysAdapter
import com.calendar.base.model.MonthItem
import com.google.android.flexbox.AlignItems
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.flexbox.JustifyContent

class MonthViewHolder(
    view: View
) : RecyclerView.ViewHolder(view) {
    private val context = view.context
    private val txtMonth: AppCompatTextView = view.findViewById(R.id.txt_month)
    private val rvDays: RecyclerView = view.findViewById(R.id.rv_days)
    private var adapter: DaysAdapter? = null
    fun bind(monthItem: MonthItem) {
        if (adapter == null) {
            rvDays.layoutManager = FlexboxLayoutManager(context).apply {
                flexDirection = FlexDirection.ROW_REVERSE
                alignItems = AlignItems.CENTER
                justifyContent = JustifyContent.FLEX_END
            }
            rvDays.setHasFixedSize(true)
            adapter = DaysAdapter()
            rvDays.adapter = adapter
        }
        adapter?.submitList(monthItem.generateDays())
        txtMonth.text = String.format("${monthItem.getYear()} - ${monthItem.getDisplayedName()}")
    }
}
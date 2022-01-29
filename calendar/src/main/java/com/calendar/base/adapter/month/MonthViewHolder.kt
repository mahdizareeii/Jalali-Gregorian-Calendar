package com.calendar.base.adapter.month

import android.view.View
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.calendar.R
import com.calendar.base.adapter.day.DaysAdapter
import com.calendar.base.model.MonthItem
import com.google.android.flexbox.AlignItems
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexWrap
import com.google.android.flexbox.FlexboxLayoutManager

class MonthViewHolder(
    view: View
) : RecyclerView.ViewHolder(view) {
    private val context = view.context
    private val txtMonth: AppCompatTextView = view.findViewById(R.id.txt_month)
    private val rvDays: RecyclerView = view.findViewById(R.id.rv_days)
    private var adapter: DaysAdapter? = null
    fun bind(monthItem: MonthItem) {
        if (adapter == null) {
            initRecyclerView()
        }
        adapter?.submitList(monthItem.generateDays())
        txtMonth.text =
            String.format("${monthItem.getDisplayedYearName()} - ${monthItem.getDisplayedMonthName()}")
    }

    private fun initRecyclerView() {
        adapter = DaysAdapter()
        rvDays.layoutManager = FlexboxLayoutManager(context).apply {
            flexDirection = FlexDirection.ROW_REVERSE
            alignItems = AlignItems.CENTER
            flexWrap = FlexWrap.WRAP
        }
        rvDays.itemAnimator = null
        rvDays.adapter = adapter
    }
}
package com.calendar.base.adapter.month

import android.view.View
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.calendar.R
import com.calendar.base.adapter.day.DaysAdapter
import com.calendar.base.types.CalendarListener
import com.calendar.base.model.MonthItem
import com.calendar.base.types.CalendarProperties
import com.calendar.base.types.CalendarType
import com.google.android.flexbox.AlignItems
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexWrap
import com.google.android.flexbox.FlexboxLayoutManager

internal class MonthViewHolder(
    view: View,
    private val calendarProperties: CalendarProperties,
    private val calendarListener: CalendarListener
) : RecyclerView.ViewHolder(view) {
    private val context = view.context
    private val txtMonth: AppCompatTextView = view.findViewById(R.id.txt_month)
    private val rvDays: RecyclerView = view.findViewById(R.id.rv_days)
    private var adapter: DaysAdapter? = null

    fun bind(monthItem: MonthItem) {
        if (adapter == null) {
            initRecyclerView()
        }
        monthItem.listener = object : MonthItem.MonthListener {
            override fun onDataSetChanged() {
                adapter?.notifyDataSetChanged()
            }
        }
        adapter?.submitList(monthItem.generateDays())
        txtMonth.text = String.format("${monthItem.getYear} - ${monthItem.getMonthName}")
    }

    private fun initRecyclerView() {
        adapter = DaysAdapter(
            calendarProperties,
            calendarListener
        )
        rvDays.layoutManager = FlexboxLayoutManager(context).apply {
            flexDirection = FlexDirection.ROW_REVERSE
            alignItems = AlignItems.CENTER
            flexWrap = FlexWrap.WRAP
        }
        rvDays.itemAnimator = null
        rvDays.adapter = adapter
    }
}
package com.calendar.base.adapter.month

import android.view.View
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.calendar.R
import com.calendar.base.adapter.day.DaysAdapter
import com.calendar.base.adapter.day.DaysListener
import com.calendar.base.model.MonthItem
import com.calendar.base.types.DaySelectionProperties
import com.calendar.base.types.DaySelectionType
import com.google.android.flexbox.AlignItems
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexWrap
import com.google.android.flexbox.FlexboxLayoutManager

internal class MonthViewHolder(
    view: View,
    private val daySelectionProperties: DaySelectionProperties,
    private val daySelectionType: DaySelectionType,
    private val daysListener: DaysListener
) : RecyclerView.ViewHolder(view) {
    private val context = view.context
    private val txtMonth: AppCompatTextView = view.findViewById(R.id.txt_month)
    private val rvDays: RecyclerView = view.findViewById(R.id.rv_days)
    private var adapter: DaysAdapter? = null
    fun bind(monthItem: MonthItem, monthItemPosition: Int) {
        if (adapter == null) {
            initRecyclerView()
        }
        adapter?.monthItemPosition = monthItemPosition
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
            daySelectionType,
            daySelectionProperties,
            daysListener
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
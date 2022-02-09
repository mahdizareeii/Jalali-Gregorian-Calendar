package com.calendar.base.adapter.month

import android.view.View
import android.widget.ImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.calendar.R
import com.calendar.base.adapter.day.DaysAdapter
import com.calendar.base.adapter.day.DaysAdapterListener
import com.calendar.base.calendar.RegionalType
import com.calendar.base.model.MonthItem
import com.calendar.base.model.MonthItemListener
import com.calendar.base.types.CalendarProperties
import com.google.android.flexbox.AlignItems
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexWrap
import com.google.android.flexbox.FlexboxLayoutManager

internal class MonthViewHolder(
    view: View,
    private val calendarProperties: CalendarProperties,
    private val daysAdapterListener: DaysAdapterListener
) : RecyclerView.ViewHolder(view) {
    private val context = view.context

    private val arrowLeft: ImageView? = view.findViewById(R.id.arrow_left) ?: null
    private val arrowRight: ImageView? = view.findViewById(R.id.arrow_right) ?: null
    private val txtMonth: AppCompatTextView = view.findViewById(R.id.txt_month)
    private val rvDays: RecyclerView = view.findViewById(R.id.rv_days)
    private var adapter: DaysAdapter? = null

    fun bind(
        monthSize: Int,
        position: Int,
        monthItem: MonthItem,
        listener: MonthAdapterListener
    ) {
        if (adapter == null) {
            initRecyclerView()
        }
        monthItem.listener = object : MonthItemListener {
            override fun onDataSetChanged() {
                adapter?.notifyDataSetChanged()
            }
        }
        adapter?.submitList(monthItem.generateDays())
        txtMonth.text = String.format("${monthItem.getYear} - ${monthItem.getMonthName}")
        initArrows(monthSize, position, listener)
    }

    private fun initRecyclerView() {
        adapter = DaysAdapter(
            calendarProperties,
            daysAdapterListener
        )
        rvDays.layoutManager = FlexboxLayoutManager(context).apply {
            flexDirection = FlexDirection.ROW_REVERSE
            alignItems = AlignItems.CENTER
            flexWrap = FlexWrap.WRAP
        }
        rvDays.setHasFixedSize(true)
        rvDays.itemAnimator = null
        rvDays.adapter = adapter
    }

    private fun initArrows(
        monthSize: Int,
        position: Int,
        listener: MonthAdapterListener
    ) {
        val isGregorianCalendar = calendarProperties.regionalType == RegionalType.Gregorian
        val arrowLeftVisibility = (position < monthSize - 1)
        val arrowRightVisibility = (position > 0)

        arrowLeft?.isVisible =
            if (isGregorianCalendar) arrowRightVisibility else arrowLeftVisibility
        arrowRight?.isVisible =
            if (isGregorianCalendar) arrowLeftVisibility else arrowRightVisibility

        arrowLeft?.setOnClickListener {
            if (isGregorianCalendar)
                listener.onRightArrowClicked()
            else
                listener.onLeftArrowClicked()
        }
        arrowRight?.setOnClickListener {
            if (calendarProperties.regionalType == RegionalType.Gregorian)
                listener.onLeftArrowClicked()
            else
                listener.onRightArrowClicked()
        }
    }
}
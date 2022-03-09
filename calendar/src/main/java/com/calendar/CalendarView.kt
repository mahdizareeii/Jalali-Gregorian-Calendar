package com.calendar

import android.content.Context
import android.util.AttributeSet
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.calendar.adapter.month.MonthAdapter
import com.calendar.adapter.month.MonthAdapterListener
import com.calendar.calendar.BaseCalendar

class CalendarView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : LinearLayout(context, attrs, defStyle) {

    lateinit var properties: CalendarProperties
    private lateinit var calendar: BaseCalendar
    private lateinit var adapter: MonthAdapter
    private lateinit var layoutManager: LinearLayoutManager
    private var recyclerView: RecyclerView

    init {
        inflate(context, R.layout.calendar, this)
        recyclerView = this.findViewById(R.id.rv_months)
    }

    /**
     *  you can increase up month and year with set field and you will get next dates
     *  @param field the field that you want increase up
     *  @param value the value that increase up field
     */
    fun submitNextDates(field: Int, value: Int) {
        initCalendar()
        adapter.submitList(calendar.getNextDates(field, value))
    }

    fun clearSelection() {
        properties.selectedCheckIn = null
        properties.selectedCheckOut = null
        properties.selectedSingle = null
        properties.selectedMultipleDay = arrayListOf()
        adapter.onDaysNotifyDataSetChanged()
    }

    fun goToMonthPosition(position: Int) = recyclerView.scrollToPosition(position)

    fun getCurrentMonthPosition() = layoutManager.findFirstVisibleItemPosition()

    private fun initCalendar() {
        calendar = properties.regionalType.calendar

        layoutManager = LinearLayoutManager(
            context,
            properties.calendarOrientation,
            properties.calendarIsReverse()
        )

        adapter = MonthAdapter(properties, object : MonthAdapterListener {
            override fun onRightArrowClicked() {
                layoutManager.scrollToPosition(layoutManager.findFirstVisibleItemPosition() - 1)
            }

            override fun onLeftArrowClicked() {
                layoutManager.scrollToPosition(layoutManager.findFirstVisibleItemPosition() + 1)
            }
        })

        if (properties.calendarOrientation == CalendarProperties.HORIZONTAL)
            PagerSnapHelper().attachToRecyclerView(recyclerView)

        recyclerView.setHasFixedSize(true)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = layoutManager
    }
}
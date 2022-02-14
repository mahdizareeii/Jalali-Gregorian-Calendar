package com.calendar

import android.content.Context
import android.util.AttributeSet
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.calendar.base.adapter.month.MonthAdapter
import com.calendar.base.adapter.month.MonthAdapterListener
import com.calendar.base.calendar.BaseCalendar

class CalendarView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : LinearLayout(context, attrs, defStyle) {

    private lateinit var calendar: BaseCalendar
    private lateinit var adapter: MonthAdapter
    private var recyclerView: RecyclerView

    init {
        inflate(context, R.layout.calendar, this)
        recyclerView = this.findViewById(R.id.rv_months)
    }

    fun submitNextDates(field: Int, value: Int) {
        adapter.submitList(calendar.getNextDates(field, value))
    }

    fun initCalendar(
        calendarProperties: CalendarProperties
    ) {
        calendar = calendarProperties.regionalType.calendar

        val layoutManager = LinearLayoutManager(
            context,
            calendarProperties.calendarOrientation,
            calendarProperties.calendarIsReverse()
        )

        adapter = MonthAdapter(calendarProperties, object : MonthAdapterListener {
            override fun onRightArrowClicked() {
                layoutManager.scrollToPosition(layoutManager.findFirstVisibleItemPosition() - 1)
            }

            override fun onLeftArrowClicked() {
                layoutManager.scrollToPosition(layoutManager.findFirstVisibleItemPosition() + 1)
            }
        })

        if (calendarProperties.calendarOrientation == CalendarProperties.HORIZONTAL)
            PagerSnapHelper().attachToRecyclerView(recyclerView)

        recyclerView.setHasFixedSize(true)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = layoutManager
    }
}
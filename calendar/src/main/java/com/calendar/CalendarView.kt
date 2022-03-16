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

    var properties: CalendarProperties? = null
        set(value) {
            field = value
            adapter?.onDaysNotifyDataSetChanged()
        }
    private var calendar: BaseCalendar? = null
    private var adapter: MonthAdapter? = null
    private var layoutManager: LinearLayoutManager? = null
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
        adapter?.submitList(calendar?.getNextDates(field, value)!!)
    }

    fun clearSelection() {
        properties?.selectedCheckIn = null
        properties?.selectedCheckOut = null
        properties?.selectedSingle = null
        properties?.selectedMultipleDay = arrayListOf()
        adapter?.onDaysNotifyDataSetChanged()
    }

    fun goToMonthPosition(position: Int) = recyclerView.scrollToPosition(position)

    fun getCurrentMonthPosition() = layoutManager?.findFirstVisibleItemPosition() ?: 0

    private fun initCalendar() {
        if (properties == null)
            throw NullPointerException("you must init properties before call submitNextDates()")
        calendar = properties?.calendar!!

        layoutManager = LinearLayoutManager(
            context,
            properties?.calendarOrientation!!,
            properties?.calendarIsReverse()!!
        )

        adapter = MonthAdapter(properties!!, object : MonthAdapterListener {
            override fun onRightArrowClicked() {
                layoutManager?.scrollToPosition(
                    (layoutManager?.findFirstVisibleItemPosition() ?: 0) - 1
                )
            }

            override fun onLeftArrowClicked() {
                layoutManager?.scrollToPosition(
                    (layoutManager?.findFirstVisibleItemPosition() ?: 0) + 1
                )
            }
        })

        if (properties?.calendarOrientation == CalendarProperties.HORIZONTAL && recyclerView.onFlingListener == null)
            PagerSnapHelper().attachToRecyclerView(recyclerView)

        recyclerView.setHasFixedSize(true)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = layoutManager
    }
}
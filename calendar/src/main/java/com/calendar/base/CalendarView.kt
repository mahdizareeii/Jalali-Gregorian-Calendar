package com.calendar.base

import android.content.Context
import android.util.AttributeSet
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.calendar.R
import com.calendar.base.adapter.month.MonthAdapter
import com.calendar.base.calendar.MyGregorianCalendar
import com.calendar.base.calendar.MyJalaliCalendar
import com.calendar.base.model.MonthItem
import com.calendar.base.model.YearItem

class CalendarView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : LinearLayout(context, attrs, defStyle) {
    //TODO handle horizontal vertical and set snap pager helper
    //TODO handle submit years
    //TODO for horizontal add PagerSnapHelper().attachToRecyclerView(recyclerView)
    //TODO create xml for properties and get it in the init

    private val jalaliCalendar = MyJalaliCalendar()
    private val gregorianCalendar = MyGregorianCalendar()

    private val adapter: MonthAdapter = MonthAdapter()
    private var recyclerView: RecyclerView
    private val months = ArrayList<MonthItem>()

    init {
        inflate(context, R.layout.calendar, this)
        recyclerView = this.findViewById(R.id.rv_months)

        initRecyclerView(LinearLayoutManager.VERTICAL)

        submitYears(
            listOf(
                YearItem(gregorianCalendar, 2022)
            )
        )
    }

    fun submitYears(years: List<YearItem>) {
        years.forEach {
            months.addAll(it.getMonthOfYear())
        }
        adapter.submitList(months)
        adapter.notifyDataSetChanged()
    }

    private fun initRecyclerView(orientation: Int) {
        if (orientation == LinearLayoutManager.HORIZONTAL)
            PagerSnapHelper().attachToRecyclerView(recyclerView)

        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(
            context,
            orientation,
            false
        )
    }
}
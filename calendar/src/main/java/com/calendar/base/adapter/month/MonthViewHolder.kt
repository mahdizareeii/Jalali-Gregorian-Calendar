package com.calendar.base.adapter.month

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.calendar.CalendarProperties
import com.calendar.R
import com.calendar.base.adapter.day.DaysAdapter
import com.calendar.base.adapter.day.DaysAdapterListener
import com.calendar.base.calendar.RegionalType
import com.calendar.base.model.Month
import com.calendar.base.model.MonthItemListener
import com.calendar.utils.setMutableDrawableColor
import com.google.android.flexbox.AlignItems
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexWrap
import com.google.android.flexbox.FlexboxLayoutManager

internal class MonthViewHolder(
    view: View,
    private val properties: CalendarProperties,
    private val daysAdapterListener: DaysAdapterListener
) : RecyclerView.ViewHolder(view) {
    private val context = view.context

    private val arrowLeft: ImageView? = view.findViewById(R.id.arrow_left) ?: null
    private val arrowRight: ImageView? = view.findViewById(R.id.arrow_right) ?: null
    private val txtMonth: AppCompatTextView = view.findViewById(R.id.txt_month)
    private val rvDays: RecyclerView = view.findViewById(R.id.rv_days)
    private val txtAgendaDesc: TextView = view.findViewById(R.id.txt_agenda_desc)
    private val imgEndAgenda: ImageView = view.findViewById(R.id.img_end_month_agenda)
    private val imgStartAgenda: ImageView = view.findViewById(R.id.img_start_month_agenda)
    private var adapter: DaysAdapter? = null

    fun bind(
        monthSize: Int,
        position: Int,
        month: Month,
        listener: MonthAdapterListener
    ) {
        if (adapter == null) {
            initRecyclerView()
        }
        month.listener = object : MonthItemListener {
            override fun onDataSetChanged() {
                adapter?.notifyDataSetChanged()
            }
        }
        adapter?.submitList(month.generateDays(properties.customDays))
        txtMonth.text = String.format("${month.getYear} - ${month.getMonthName}")
        initArrows(monthSize, position, listener)
        initAgendaDesc(month)
        initAgendaRangeDesc(month)
    }

    private fun initRecyclerView() {
        adapter = DaysAdapter(
            properties,
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
        val isGregorianCalendar = properties.regionalType == RegionalType.Gregorian
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
            if (isGregorianCalendar)
                listener.onLeftArrowClicked()
            else
                listener.onRightArrowClicked()
        }
    }

    private fun initAgendaDesc(month: Month) {
        if (!properties.isAgendaDays) return
        val agendaDays = properties.findMonthInAgendaList(month)
        val agendaVisibility = agendaDays != null
        imgEndAgenda.isVisible = false
        imgStartAgenda.isVisible = agendaVisibility
        txtAgendaDesc.isVisible = agendaVisibility
        imgStartAgenda.setMutableDrawableColor(agendaDays?.getAgendaColor())
        txtAgendaDesc.text = agendaDays?.title ?: "-"
    }

    private fun initAgendaRangeDesc(month: Month) {
        if (!properties.isAgendaRangeDays) return
        val agendaRangeDays = properties.findMonthInAgendaRangeList(month)
        val agendaRangeVisibility = agendaRangeDays != null
        imgEndAgenda.isVisible = agendaRangeVisibility
        imgStartAgenda.isVisible = agendaRangeVisibility
        txtAgendaDesc.isVisible = agendaRangeVisibility
        imgStartAgenda.setMutableDrawableColor(agendaRangeDays?.getAgendaColor())
        imgEndAgenda.setMutableDrawableColor(agendaRangeDays?.getAgendaColor())
        txtAgendaDesc.text = agendaRangeDays?.title ?: "-"
    }
}
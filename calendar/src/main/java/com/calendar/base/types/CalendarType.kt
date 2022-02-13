package com.calendar.base.types

import android.content.Context
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import com.calendar.R
import com.calendar.base.adapter.day.DayViewHolder
import com.calendar.base.adapter.day.DaysAdapterListener
import com.calendar.base.calendar.MyJalaliCalendar
import com.calendar.base.model.DayItem
import com.calendar.base.types.multipleselection.MultipleSelection
import com.calendar.base.types.rangeslelection.RangeSelection
import com.calendar.base.types.singleselection.SingleSelection
import com.calendar.utils.DateUtil

abstract class CalendarType {
    protected lateinit var context: Context

    internal abstract val onDayClickListener: (
        viewHolder: DayViewHolder,
        dayItem: DayItem,
        properties: CalendarProperties,
        listener: DaysAdapterListener
    ) -> Unit

    internal abstract fun dayBackground(
        viewHolder: DayViewHolder,
        dayItem: DayItem,
        properties: CalendarProperties,
    )

    internal open fun bind(
        viewHolder: DayViewHolder,
        dayItem: DayItem,
        properties: CalendarProperties,
        listener: DaysAdapterListener
    ) {
        context = viewHolder.itemView.context
        textColor(viewHolder, dayItem, properties)
        dayBackground(viewHolder, dayItem, properties)
        viewHolder.bgDay.visibility = dayItem.dayVisibility
        viewHolder.bgDay.setOnClickListener {
            onDayClickListener.invoke(viewHolder, dayItem, properties, listener)
        }
        viewHolder.txtDay.text = dayItem.day.toString()
        viewHolder.txtPrice.isVisible = properties.showDaysPrice
        viewHolder.txtPrice.text = dayItem.getPrice()
    }

    private fun textColor(
        viewHolder: DayViewHolder,
        currentItem: DayItem,
        properties: CalendarProperties
    ) {
        val color = ContextCompat.getColor(
            context,
            when {
                currentItem.isHoliday -> R.color.red
                else -> {
                    if (isSelected(currentItem, properties)) R.color.white
                    else R.color.secondary
                }
            }
        )
        viewHolder.txtDay.setTextColor(color)
        viewHolder.txtPrice.setTextColor(color)
    }

    internal fun checkAvailability(
        viewHolder: DayViewHolder,
        currentItem: DayItem,
        properties: CalendarProperties
    ): Boolean {
        val availableFromToday =
            if (properties.getToday() == null) true
            else currentItem >= properties.getToday()!!

        val baseAvailability = availableFromToday && !currentItem.isDisable

        val availability = if (properties.showDaysPrice)
            when (properties.calendarType::class.java) {
                RangeSelection::class.java -> {
                    when {
                        properties.isCheckInSelect() -> {
                            val difDays = if (currentItem.isNotNull() && currentItem > properties.selectedCheckIn!!) {
                                if (properties.regionalType.calendar is MyJalaliCalendar) {
                                    DateUtil.diffDaysJalali(
                                        properties.selectedCheckIn,
                                        currentItem
                                    )
                                } else {
                                    DateUtil.diffDaysGregorian(
                                        properties.selectedCheckIn,
                                        currentItem,
                                    )
                                }
                            } else if (currentItem.isNotNull() && currentItem < properties.selectedCheckIn!!)
                                if (properties.regionalType.calendar is MyJalaliCalendar) {
                                    DateUtil.diffDaysJalali(
                                        currentItem,
                                        properties.selectedCheckIn
                                    )
                                } else {
                                    DateUtil.diffDaysGregorian(
                                        currentItem,
                                        properties.selectedCheckIn
                                    )
                                }
                            else {
                                properties.minDaysInRangeSelection
                            }

                            availableFromToday &&
                                    difDays >= properties.minDaysInRangeSelection &&
                                    !properties.customDays.any {
                                        it < currentItem && properties.selectedCheckIn!! < it && it.isDisable
                                    } &&
                                    !properties.customDays.any {
                                        it >= currentItem && properties.selectedCheckIn!! > it && it.isDisable
                                    }
                        }
                        properties.isCheckOutSelect() -> {
                            if (currentItem.isDisable && currentItem == properties.selectedCheckOut)
                                true
                            else
                                baseAvailability
                        }
                        else -> baseAvailability
                    }
                }

                else -> baseAvailability
            }
        else baseAvailability

        if (availability)
            setAvailableBackground(viewHolder)
        else
            setUnAvailableBackground(viewHolder)

        return availability
    }

    private fun setAvailableBackground(viewHolder: DayViewHolder) {
        viewHolder.bgDay.background = ContextCompat.getDrawable(
            context,
            R.drawable.bg_day
        )?.apply {
            alpha = 255
        }
        viewHolder.txtDay.alpha = 1f
        viewHolder.txtPrice.alpha = 1f
    }

    private fun setUnAvailableBackground(viewHolder: DayViewHolder) {
        viewHolder.bgDay.background = ContextCompat.getDrawable(
            context,
            R.drawable.bg_disabled_date
        )?.apply {
            alpha = 0X3C
        }
        viewHolder.txtDay.alpha = 0.3f
        viewHolder.txtPrice.alpha = 0.3f
    }

    private fun isSelected(
        currentItem: DayItem,
        properties: CalendarProperties
    ): Boolean {
        return when (properties.calendarType::class.java) {
            RangeSelection::class.java -> {
                if (properties.selectedCheckIn != null && properties.selectedCheckOut != null)
                    return currentItem >= properties.selectedCheckIn!! && currentItem <= properties.selectedCheckOut!!

                if (properties.selectedCheckIn != null)
                    return currentItem == properties.selectedCheckIn

                if (properties.selectedCheckOut != null)
                    return currentItem == properties.selectedCheckOut

                return false
            }

            SingleSelection::class.java -> {
                return if (properties.selectedSingle != null)
                    properties.selectedSingle == currentItem
                else false
            }

            MultipleSelection::class.java -> {
                properties.selectedMultipleDayItem.any {
                    it == currentItem
                }
            }

            else -> false
        }
    }
}
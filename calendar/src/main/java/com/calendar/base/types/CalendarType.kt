package com.calendar.base.types

import android.content.Context
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import androidx.core.view.isVisible
import com.calendar.CalendarProperties
import com.calendar.R
import com.calendar.base.adapter.day.DayViewHolder
import com.calendar.base.adapter.day.DaysAdapterListener
import com.calendar.base.model.DayItem
import com.calendar.base.types.multipleselection.MultipleSelection
import com.calendar.base.types.rangeslelection.RangeSelection
import com.calendar.base.types.singleselection.SingleSelection

/**
 * @property context init with viewHolder item view context
 * @property onDayClickListener when day of month clicked this fun will invoke
 * @property dayBackground decision for day item background base on implementation of each class
 */
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
        viewHolder.txtPrice.isVisible = properties.txtPriceVisibility(dayItem)
        viewHolder.txtPrice.text = dayItem.getPrice()
        initAgendaDay(viewHolder, dayItem, properties)
    }

    private fun initAgendaDay(
        viewHolder: DayViewHolder,
        dayItem: DayItem,
        properties: CalendarProperties
    ) {
        viewHolder.imgStartAgenda.isVisible =
            !isSelected(dayItem, properties) && properties.imgStartAgendaVisibility(dayItem)
        setMutableDrawableColor(
            viewHolder.imgStartAgenda,
            properties.imgStartAgendaColor(dayItem)
        )
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

    private fun setMutableDrawableColor(imageView: ImageView, color: Int?) {
        color ?: return
        DrawableCompat.wrap(imageView.drawable).also {
            it.mutate().also { drawable ->
                DrawableCompat.setTint(drawable, color)
            }
        }
    }

    /**
     * @return selection state base on CalendarType
     * @param currentItem the current day item
     * @param properties for check the properties base on CalendarType
     */
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

    internal fun checkAvailability(
        viewHolder: DayViewHolder,
        currentItem: DayItem,
        properties: CalendarProperties
    ): Boolean {
        val availability = properties.availabilityRule.isAvailable(currentItem, properties)
        if (availability) setAvailableBackground(viewHolder)
        else setUnAvailableBackground(viewHolder)
        return availability
    }


}
package com.calendar.base.adapter.day.viewholder

import android.view.View
import androidx.core.view.isVisible
import com.calendar.CalendarProperties
import com.calendar.base.adapter.day.DaysAdapterListener
import com.calendar.base.model.Day
import com.calendar.utils.PriceFormatter
import kotlin.math.abs

internal open class DayPriceViewHolder(
    view: View,
    properties: CalendarProperties,
    listener: DaysAdapterListener
):DayViewHolder(view, properties, listener) {

    override fun bind(day: Day) {
        super.bind(day)
        txtPrice.isVisible = properties.txtPriceVisibility(day)
        txtPrice.text = getPrice(day)
    }

    /**
     * @return formatted price of date as string
     */
    private fun getPrice(day: Day): String {
        val price = if (day.discount != null && day.discount != 0.0)
            ((day.price ?: 0.0) - ((day.price ?: 0.0) * ((day.discount ?: 0.0) / 100))).div(1000)
        else
            (day.price ?: 0.0).div(1000)
        return if (price <= 0.0) "-" else PriceFormatter.formatPrice(abs(price))
    }
}
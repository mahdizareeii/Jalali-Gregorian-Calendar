package com.calendar.adapter.day.viewholder

import android.view.View
import androidx.core.view.isVisible
import com.calendar.CalendarProperties
import com.calendar.adapter.day.DaysAdapterListener
import com.calendar.model.Day
import com.calendar.utils.PriceFormatter
import kotlin.math.abs

internal open class DayPriceViewHolder(
    view: View,
    properties: CalendarProperties,
    listener: DaysAdapterListener
) : DayViewHolder(view, properties, listener) {

    override fun bind(day: Day, position: Int) {
        super.bind(day, position)
        txtPrice.isVisible = txtPriceVisibility(day)
        txtPrice.text = getPrice(day)
    }

    private fun txtPriceVisibility(currentDay: Day) = properties.selectedCheckOut != currentDay

    private fun getPrice(day: Day): String {
        val price = if (day.discount != null && day.discount != 0.0)
            ((day.price ?: 0.0) - ((day.price ?: 0.0) * ((day.discount ?: 0.0) / 100))).div(1000)
        else
            (day.price ?: 0.0).div(1000)
        return if (price <= 0.0) "-" else PriceFormatter.formatPrice(abs(price))
    }
}
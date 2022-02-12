package com.calendar.utils

import java.text.DecimalFormat
import java.util.*

object PriceFormatter {
    private val formatter: DecimalFormat = (DecimalFormat.getInstance(Locale.US) as DecimalFormat)
        .apply { applyPattern("#,###") }

    fun formatPrice(price: Double?) = if (price == null) "" else formatter.format(price) ?: ""
}
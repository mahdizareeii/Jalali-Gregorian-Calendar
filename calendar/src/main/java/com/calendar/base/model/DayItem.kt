package com.calendar.base.model

import android.view.View
import com.calendar.utils.PriceFormatter
import kotlin.math.abs

/**
 * Gregorian date sample
 * @sample DayItem(2050,1,1)
 * Jalali date sample
 * @sample DayItem(1420,1,1)
 *
 * @param year hold every year that you give
 * @param month hold every month that you give
 * @param day hold every day that you give
 *
 * @property gregorianYear hold gregorian year of jalali
 * @property gregorianMonth hold gregorian month of jalali
 * @property gregorianDay hold gregorian day of jalali
 */
data class DayItem constructor(
    val year: Int?,
    val month: Int?,
    val day: Int?
) {

    var gregorianYear: Int? = null
    var gregorianMonth: Int? = null
    var gregorianDay: Int? = null

    var price: Double? = null
    var discount: Double? = null
    var isHoliday: Boolean = false
    var isDisable: Boolean = false

    val dayVisibility get() = if (day != null) View.VISIBLE else View.INVISIBLE

    /**
     * @param price hold price of the date
     * @param isHoliday if be true the color of date in calendar will change
     * @param isDisable if be true the date of calendar will disable
     */
    constructor(
        year: Int?,
        month: Int?,
        day: Int?,
        price: Double?,
        isHoliday: Boolean = false,
        isDisable: Boolean = true
    ) : this(year, month, day) {
        this.price = price
        this.isHoliday = isHoliday
        this.isDisable = isDisable
    }

    override fun toString(): String {
        return "$year-$month-$day"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as DayItem

        if (year != other.year) return false
        if (month != other.month) return false
        if (day != other.day) return false

        return true
    }

    override fun hashCode(): Int {
        var result = year ?: 0
        result = 31 * result + (month ?: 0)
        result = 31 * result + (day ?: 0)
        return result
    }

    operator fun compareTo(other: DayItem?): Int {
        return when {
            this === other -> 0
            year == other?.year && month == other?.month && day == other?.day -> 0
            (year ?: 0) > (other?.year ?: 0) -> 1
            year == other?.year -> {
                when {
                    month == other?.month -> {
                        if ((day ?: 0) > (other?.day ?: 0)) 1
                        else -1
                    }
                    (month ?: 0) > (other?.month ?: 0) -> 1
                    else -> -1
                }
            }
            else -> -1
        }
    }

    /**
     * @param year the converted year of jalali date to gregorian
     * @param month the converted month of jalali date to gregorian
     * @param day the converted day of jalali date to gregorian
     */
    fun setGregorianDate(year: Int, month: Int, day: Int) {
        this.gregorianYear = year
        this.gregorianMonth = month
        this.gregorianDay = day
    }

    /**
     * @return formatted date as string "yyyy-MM-dd"
     */
    fun getGregorianDate(): String = "$gregorianYear-$gregorianMonth-$gregorianDay"

    /**
     * @return formatted price of date as string
     */
    fun getPrice(): String {
        val price = if (discount != null && discount != 0.0)
            ((price ?: 0.0) - ((price ?: 0.0) * ((discount ?: 0.0) / 100))).div(1000)
        else
            (price ?: 0.0).div(1000)
        return if (price <= 0.0) "-" else PriceFormatter.formatPrice(abs(price))
    }

    fun isNotNull() = year != null && month != null && day != null
}

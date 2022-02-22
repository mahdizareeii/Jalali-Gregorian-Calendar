package com.calendar.model

import android.view.View

/**
 * Gregorian date sample
 * @sample Day(2050,1,1)
 * Jalali date sample
 * @sample Day(1420,1,1)
 *
 * @param year hold every year that you give
 * @param month hold every month that you give (must be start with 1)
 * @param day hold every day that you give
 */
data class Day constructor(
    val year: Int?,
    val month: Int?,
    val day: Int?,
    val isGregorianDate: Boolean?
) {

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
        isGregorianDate: Boolean,
        price: Double?,
        isHoliday: Boolean = false,
        isDisable: Boolean = true
    ) : this(year, month, day, isGregorianDate) {
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

        other as Day

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

    operator fun compareTo(other: Day?): Int {
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

    fun isNotNull() = year != null && month != null && day != null
}

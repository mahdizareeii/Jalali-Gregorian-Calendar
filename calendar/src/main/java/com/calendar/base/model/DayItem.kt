package com.calendar.base.model

import android.view.View

data class DayItem constructor(
    val year: Int?,
    val month: Int?,
    val day: Int?
) {
    val visibility = if (day != null) View.VISIBLE else View.INVISIBLE
    var price: Double? = null
    var isHoliday: Boolean = false
    var isDisable: Boolean = false

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
        return "$year - $month - $day"
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

    operator fun compareTo(other: DayItem): Int {
        return when {
            this === other -> 0
            year == other.year && month == other.month && day == other.day -> 0
            (year ?: 0) > (other.year ?: 0) -> 1
            year == other.year -> {
                when {
                    month == other.month -> {
                        if ((day ?: 0) > (other.day ?: 0)) 1
                        else -1
                    }
                    (month ?: 0) > (other.month ?: 0) -> 1
                    else -> -1
                }
            }
            else -> -1
        }
    }

    override fun hashCode(): Int {
        var result = year ?: 0
        result = 31 * result + (month ?: 0)
        result = 31 * result + (day ?: 0)
        return result
    }
}

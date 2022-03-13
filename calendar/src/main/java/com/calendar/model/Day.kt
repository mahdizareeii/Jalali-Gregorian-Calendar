package com.calendar.model

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
    val year: Int,
    val month: Int,
    val day: Int
) {

    var price: Double? = null
    var discount: Double? = null
    var isHoliday: Boolean = false
    var status: DayStatus = DayStatus.AVAILABLE

    /**
     * @param price hold price of the date
     * @param isHoliday if be true the color of date in calendar will change
     * @param status status of a day
     */
    constructor(
        year: Int,
        month: Int,
        day: Int,
        price: Double?,
        isHoliday: Boolean = false,
        status: DayStatus = DayStatus.AVAILABLE
    ) : this(year, month, day) {
        this.price = price
        this.isHoliday = isHoliday
        this.status = status
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
        var result = year
        result = 31 * result + month
        result = 31 * result + day
        return result
    }

    operator fun compareTo(other: Day?): Int {
        return when {
            this === other -> 0
            year == other?.year && month == other.month && day == other.day -> 0
            year > (other?.year ?: 0) -> 1
            year == other?.year -> {
                when {
                    month == other.month -> {
                        if (day > other.day) 1
                        else -1
                    }
                    month > other.month -> 1
                    else -> -1
                }
            }
            else -> -1
        }
    }

    fun isEmptyDay() = year == -1 && month == -1 && day == -1
}
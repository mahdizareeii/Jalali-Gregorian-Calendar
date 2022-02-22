package com.calendar

import androidx.annotation.IntRange
import androidx.recyclerview.widget.RecyclerView
import com.calendar.adapter.day.viewholder.DayViewHolderType
import com.calendar.availablity.BaseAvailabilityRule
import com.calendar.calendar.RegionalType
import com.calendar.model.Day
import com.calendar.model.Month
import com.calendar.model.agenda.AgendaDayRange
import com.calendar.model.agenda.AgendaDays
import com.calendar.types.CalendarType

/**
 *  @property regionalType set regional type of calendar
 *  @property calendarType set calendar view type
 *  @property calendarOrientation set orientation of calendar (HORIZONTAL,VERTICAL)
 *  @property showDaysPrice if be true the calendar will show prices that you gave from customDays
 *  @property availabilityRule for decision of check days availability
 */
class CalendarProperties {
    val regionalType: RegionalType
    val calendarType: CalendarType
    var calendarOrientation: Int = 0
    var showDaysPrice: Boolean = false

    @IntRange(from = 1)
    var minDaysInRangeSelection: Int = 1
    var availabilityRule: BaseAvailabilityRule

    //for set custom days pricing and etc
    var customDays: ArrayList<Day> = ArrayList()

    //for range selection
    var selectedCheckIn: Day? = null
    var selectedCheckOut: Day? = null

    //for multiple selection
    var selectedMultipleDay: ArrayList<Day> = ArrayList()

    //for single selection
    var selectedSingle: Day? = null

    //for AgendaDaysPriceViewHolder
    var agendaDays: ArrayList<AgendaDays> = ArrayList()

    //for AgendaRangeDaysViewHolder
    var agendaRangeDays: ArrayList<AgendaDayRange> = ArrayList()

    private constructor(
        regionalType: RegionalType,
        calendarType: CalendarType,
        calendarOrientation: Int,
        showDaysPrice: Boolean,
        @IntRange(from = 1) minDaysInRangeSelection: Int = 1,
        availabilityRule: BaseAvailabilityRule,
        customDays: ArrayList<Day> = ArrayList(),
        selectedCheckIn: Day? = null,
        selectedCheckOut: Day? = null,
        selectedMultipleDay: ArrayList<Day> = ArrayList(),
        selectedSingle: Day? = null
    ) {
        this.regionalType = regionalType
        this.calendarType = calendarType
        this.calendarOrientation = calendarOrientation
        this.showDaysPrice = showDaysPrice
        this.minDaysInRangeSelection = minDaysInRangeSelection
        this.availabilityRule = availabilityRule
        this.customDays = customDays
        this.selectedCheckIn = selectedCheckIn
        this.selectedCheckOut = selectedCheckOut
        this.selectedMultipleDay = selectedMultipleDay
        this.selectedSingle = selectedSingle
    }

    /**
     * AgendaDaysPriceViewHolder
     */
    constructor(
        regionalType: RegionalType,
        calendarType: CalendarType,
        calendarOrientation: Int,
        @IntRange(from = 1) minDaysInRangeSelection: Int = 1,
        availabilityRule: BaseAvailabilityRule,
        agendaDays: ArrayList<AgendaDays>,
        customDays: ArrayList<Day>,
        selectedCheckIn: Day? = null,
        selectedCheckOut: Day? = null,
        selectedMultipleDay: ArrayList<Day> = arrayListOf(),
        selectedSingle: Day? = null
    ) : this(
        regionalType = regionalType,
        calendarType = calendarType,
        calendarOrientation = calendarOrientation,
        showDaysPrice = true,
        minDaysInRangeSelection = minDaysInRangeSelection,
        availabilityRule = availabilityRule,
        customDays = customDays,
        selectedCheckIn = selectedCheckIn,
        selectedCheckOut = selectedCheckOut,
        selectedMultipleDay = selectedMultipleDay,
        selectedSingle = selectedSingle
    ) {
        dayViewHolderType = DayViewHolderType.AgendaDaysPriceViewHolder
        this.agendaDays = agendaDays
    }

    /**
     * AgendaRangeDaysViewHolder
     */
    constructor(
        regionalType: RegionalType,
        calendarType: CalendarType,
        calendarOrientation: Int,
        availabilityRule: BaseAvailabilityRule,
        agendaRangeDays: ArrayList<AgendaDayRange> = arrayListOf()
    ) : this(
        regionalType = regionalType,
        calendarType = calendarType,
        calendarOrientation = calendarOrientation,
        showDaysPrice = false,
        availabilityRule = availabilityRule
    ) {
        dayViewHolderType = DayViewHolderType.AgendaRangeDaysViewHolder
        this.agendaRangeDays = agendaRangeDays
    }

    /**
     * DayPriceViewHolder
     */
    constructor(
        regionalType: RegionalType,
        calendarType: CalendarType,
        calendarOrientation: Int,
        @IntRange(from = 1) minDaysInRangeSelection: Int = 1,
        availabilityRule: BaseAvailabilityRule,
        customDays: ArrayList<Day>,
        selectedCheckIn: Day? = null,
        selectedCheckOut: Day? = null,
        selectedMultipleDay: ArrayList<Day> = arrayListOf(),
        selectedSingle: Day? = null
    ) : this(
        regionalType = regionalType,
        calendarType = calendarType,
        calendarOrientation = calendarOrientation,
        showDaysPrice = false,
        minDaysInRangeSelection = minDaysInRangeSelection,
        availabilityRule = availabilityRule,
        customDays = customDays,
        selectedCheckIn = selectedCheckIn,
        selectedCheckOut = selectedCheckOut,
        selectedMultipleDay = selectedMultipleDay,
        selectedSingle = selectedSingle
    ) {
        dayViewHolderType = DayViewHolderType.DayPriceViewHolder
    }

    /**
     * DayViewHolder
     */
    constructor(
        regionalType: RegionalType,
        calendarType: CalendarType,
        calendarOrientation: Int,
        @IntRange(from = 1) minDaysInRangeSelection: Int = 1,
        availabilityRule: BaseAvailabilityRule,
        selectedCheckIn: Day? = null,
        selectedCheckOut: Day? = null,
        selectedMultipleDay: ArrayList<Day> = arrayListOf(),
        selectedSingle: Day? = null
    ) : this(
        regionalType = regionalType,
        calendarType = calendarType,
        calendarOrientation = calendarOrientation,
        showDaysPrice = true,
        minDaysInRangeSelection = minDaysInRangeSelection,
        availabilityRule = availabilityRule,
        selectedCheckIn = selectedCheckIn,
        selectedCheckOut = selectedCheckOut,
        selectedMultipleDay = selectedMultipleDay,
        selectedSingle = selectedSingle
    ) {
        dayViewHolderType = DayViewHolderType.DayViewHolder
    }

    internal var dayViewHolderType = DayViewHolderType.Unknown

    internal fun calendarIsReverse() =
        regionalType == RegionalType.Jalali && calendarOrientation == HORIZONTAL

    internal fun isCheckInSelect() = selectedCheckIn != null && selectedCheckOut == null

    internal fun isCheckOutSelect() = selectedCheckIn != null && selectedCheckOut != null

    internal fun getToday(): Day = regionalType.calendar.getToday()

    internal fun findMonthInAgendaList(month: Month) = agendaDays.firstOrNull {
        it.agendaList.firstOrNull { day ->
            day.year == month.getYear &&
                    day.month == (month.getMonth + 1)
        } != null
    }

    internal fun findMonthInAgendaRangeList(month: Month) = agendaRangeDays.firstOrNull {
        it.agendaRangeList.firstOrNull { range ->
            range.startDate.year == month.getYear &&
                    range.startDate.month == (month.getMonth + 1) ||
                    range.endDate.year == month.getYear &&
                    range.endDate.month == (month.getMonth + 1)
        } != null
    }

    companion object {
        const val HORIZONTAL = RecyclerView.HORIZONTAL
        const val VERTICAL = RecyclerView.VERTICAL
    }
}
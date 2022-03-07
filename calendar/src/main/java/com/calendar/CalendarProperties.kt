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
 *  @property showBubbleMessage if be true the calendar will show bubble message when select date
 *  @property availabilityRule for decision of check days availability
 */
class CalendarProperties {
    val regionalType: RegionalType
    val calendarType: CalendarType
    var calendarOrientation: Int = 0
    var showBubbleMessage: Boolean = false

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
        availabilityRule: BaseAvailabilityRule,
        @IntRange(from = 1) minDaysInRangeSelection: Int = 1,
        customDays: ArrayList<Day> = ArrayList(),
        selectedCheckIn: Day? = null,
        selectedCheckOut: Day? = null,
        selectedMultipleDay: ArrayList<Day> = ArrayList(),
        selectedSingle: Day? = null
    ) {
        this.regionalType = regionalType
        this.calendarType = calendarType
        this.calendarOrientation = calendarOrientation
        this.availabilityRule = availabilityRule
        this.minDaysInRangeSelection = minDaysInRangeSelection
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
        availabilityRule: BaseAvailabilityRule,
        showBubbleMessage: Boolean,
        @IntRange(from = 1) minDaysInRangeSelection: Int = 1,
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
        availabilityRule = availabilityRule,
        minDaysInRangeSelection = minDaysInRangeSelection,
        customDays = customDays,
        selectedCheckIn = selectedCheckIn,
        selectedCheckOut = selectedCheckOut,
        selectedMultipleDay = selectedMultipleDay,
        selectedSingle = selectedSingle
    ) {
        dayViewHolderType = DayViewHolderType.AgendaDaysPriceViewHolder
        this.agendaDays = agendaDays
        this.showBubbleMessage = showBubbleMessage
    }

    /**
     * AgendaRangeDaysViewHolder
     */
    constructor(
        regionalType: RegionalType,
        calendarType: CalendarType,
        calendarOrientation: Int,
        availabilityRule: BaseAvailabilityRule,
        agendaRangeDays: ArrayList<AgendaDayRange>
    ) : this(
        regionalType = regionalType,
        calendarType = calendarType,
        calendarOrientation = calendarOrientation,
        availabilityRule = availabilityRule,
        customDays = arrayListOf()
    ) {
        dayViewHolderType = DayViewHolderType.AgendaRangeDaysViewHolder
        this.agendaRangeDays = agendaRangeDays
    }

    /**
     * DayViewHolder
     */
    constructor(
        regionalType: RegionalType,
        calendarType: CalendarType,
        calendarOrientation: Int,
        availabilityRule: BaseAvailabilityRule,
        @IntRange(from = 1) minDaysInRangeSelection: Int = 1,
        selectedCheckIn: Day? = null,
        selectedCheckOut: Day? = null,
        selectedMultipleDay: ArrayList<Day> = arrayListOf(),
        selectedSingle: Day? = null
    ) : this(
        regionalType = regionalType,
        calendarType = calendarType,
        calendarOrientation = calendarOrientation,
        availabilityRule = availabilityRule,
        minDaysInRangeSelection = minDaysInRangeSelection,
        customDays = arrayListOf(),
        selectedCheckIn = selectedCheckIn,
        selectedCheckOut = selectedCheckOut,
        selectedMultipleDay = selectedMultipleDay,
        selectedSingle = selectedSingle
    ) {
        dayViewHolderType = DayViewHolderType.DayViewHolder
    }

    /**
     * DayPriceViewHolder
     */
    constructor(
        regionalType: RegionalType,
        calendarType: CalendarType,
        calendarOrientation: Int,
        showBubbleMessage: Boolean,
        availabilityRule: BaseAvailabilityRule,
        @IntRange(from = 1) minDaysInRangeSelection: Int = 1,
        customDays: ArrayList<Day>,
        selectedCheckIn: Day? = null,
        selectedCheckOut: Day? = null,
        selectedMultipleDay: ArrayList<Day> = arrayListOf(),
        selectedSingle: Day? = null,
    ) : this(
        regionalType = regionalType,
        calendarType = calendarType,
        calendarOrientation = calendarOrientation,
        availabilityRule = availabilityRule,
        minDaysInRangeSelection = minDaysInRangeSelection,
        customDays = customDays,
        selectedCheckIn = selectedCheckIn,
        selectedCheckOut = selectedCheckOut,
        selectedMultipleDay = selectedMultipleDay,
        selectedSingle = selectedSingle
    ) {
        dayViewHolderType = DayViewHolderType.DayPriceViewHolder
        this.showBubbleMessage = showBubbleMessage
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
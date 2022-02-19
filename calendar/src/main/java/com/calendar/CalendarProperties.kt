package com.calendar

import androidx.annotation.IntRange
import androidx.recyclerview.widget.RecyclerView
import com.calendar.base.availablity.BaseAvailabilityRule
import com.calendar.base.calendar.RegionalType
import com.calendar.base.model.Day
import com.calendar.base.model.Month
import com.calendar.base.model.agenda.AgendaDayRange
import com.calendar.base.model.agenda.AgendaDays
import com.calendar.base.types.CalendarType

/**
 *  @property regionalType set regional type of calendar
 *  @property calendarType set calendar view type
 *  @property calendarOrientation set orientation of calendar (HORIZONTAL,VERTICAL)
 *  @property showDaysPrice if be true the calendar will show prices that you gave from customDays
 *  @property availabilityRule for decision of check days availability
 */
data class CalendarProperties(
    val regionalType: RegionalType,
    val calendarType: CalendarType,
    val calendarOrientation: Int,
    val showDaysPrice: Boolean,
    @IntRange(from = 1) val minDaysInRangeSelection: Int = 1,
    val availabilityRule: BaseAvailabilityRule,
    //for show flag for custom days
    val agendaDays: ArrayList<AgendaDays> = ArrayList(),
    //for show flag for custom days
    val agendaRangeDays: ArrayList<AgendaDayRange> = ArrayList(),
    //for set custom days pricing and etc
    var customDays: ArrayList<Day> = ArrayList(),

    //for range selection
    var selectedCheckIn: Day? = null,
    var selectedCheckOut: Day? = null,

    //for multiple selection
    val selectedMultipleDay: ArrayList<Day> = ArrayList(),

    //for single selection
    var selectedSingle: Day? = null
) {

    internal val isAgendaDays get() = !agendaDays.isNullOrEmpty()
    internal val isAgendaRangeDays get() = !agendaRangeDays.isNullOrEmpty()

    internal fun calendarIsReverse() =
        regionalType == RegionalType.Jalali && calendarOrientation == HORIZONTAL

    internal fun isCheckInSelect() = selectedCheckIn != null && selectedCheckOut == null

    internal fun isCheckOutSelect() = selectedCheckIn != null && selectedCheckOut != null

    internal fun getToday(): Day = regionalType.calendar.getToday()

    internal fun findMonthInAgendaList(month: Month) = agendaDays.firstOrNull {
        it.agendaList.firstOrNull { day ->
            day.year == month.getYear &&
                    day.month == month.getMonth
        } != null
    }

    internal fun findMonthInAgendaRangeList(month: Month) = agendaRangeDays.firstOrNull {
        it.agendaRangeList.firstOrNull { range ->
            range.startDate.year == month.getYear &&
                    range.startDate.month == month.getMonth ||
                    range.endDate.year == month.getYear &&
                    range.endDate.month == month.getMonth
        } != null
    }

    companion object {
        const val HORIZONTAL = RecyclerView.HORIZONTAL
        const val VERTICAL = RecyclerView.VERTICAL
    }
}
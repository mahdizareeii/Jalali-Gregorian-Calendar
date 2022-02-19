package com.calendar

import androidx.annotation.IntRange
import androidx.recyclerview.widget.RecyclerView
import com.calendar.base.availablity.BaseAvailabilityRule
import com.calendar.base.calendar.RegionalType
import com.calendar.base.model.AgendaDays
import com.calendar.base.model.DayItem
import com.calendar.base.model.MonthItem
import com.calendar.base.types.CalendarType
import com.calendar.base.types.multipleselection.MultipleSelection
import com.calendar.base.types.rangeslelection.RangeSelection
import com.calendar.base.types.singleselection.SingleSelection

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
    //for set custom days pricing and etc
    var customDays: ArrayList<DayItem> = ArrayList(),

    //for range selection
    var selectedCheckIn: DayItem? = null,
    var selectedCheckOut: DayItem? = null,

    //for multiple selection
    val selectedMultipleDayItem: ArrayList<DayItem> = ArrayList(),

    //for single selection
    var selectedSingle: DayItem? = null
) {
    internal fun txtPriceVisibility(currentItem: DayItem) =
        showDaysPrice && selectedCheckOut != currentItem

    internal fun imgStartAgendaVisibility(currentItem: DayItem) =
        agendaDays.any { it.daysList.any { day -> day == currentItem } }

    internal fun imgStartAgendaColor(currentItem: DayItem) =
        agendaDays.firstOrNull {
            it.daysList.firstOrNull { day -> day == currentItem } != null
        }?.getAgendaColor()

    internal fun calendarIsReverse() =
        regionalType == RegionalType.Jalali && calendarOrientation == HORIZONTAL

    internal fun isCheckInSelect() = selectedCheckIn != null && selectedCheckOut == null

    internal fun isCheckOutSelect() = selectedCheckIn != null && selectedCheckOut != null

    internal fun getToday(): DayItem = regionalType.calendar.getToday()

    internal fun findMonthInAgendaList(monthItem: MonthItem) = agendaDays.firstOrNull {
        it.daysList.firstOrNull { day ->
            day.year == monthItem.getYear &&
                    day.month == monthItem.getMonth
        } != null
    }

    companion object {
        const val HORIZONTAL = RecyclerView.HORIZONTAL
        const val VERTICAL = RecyclerView.VERTICAL
    }
}
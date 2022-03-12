package com.calendar.types.rangeslelection

import android.content.Context
import android.text.SpannableString
import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.IntRange
import androidx.core.content.ContextCompat
import androidx.core.view.doOnLayout
import com.calendar.CalendarProperties
import com.calendar.R
import com.calendar.adapter.day.DaysAdapterListener
import com.calendar.adapter.day.viewholder.DayViewHolder
import com.calendar.calendar.RegionalType
import com.calendar.model.Day
import com.calendar.types.CalendarType
import com.calendar.utils.DateUtil
import com.calendar.utils.bubbleview.BubbleLayout
import com.calendar.utils.bubbleview.BubblePopupHelper
import com.calendar.utils.dp
import com.calendar.utils.setBackgroundFromDrawable

class RangeSelection(
    @IntRange(from = 1)
    val minDaysInRangeSelection: Int = 1,
    private val showBubbleMessage: Boolean,
    private val rangeSelectionListener: RangeSelectionListener
) : CalendarType() {

    override val onDayClickListener: (
        viewHolder: DayViewHolder,
        day: Day,
        properties: CalendarProperties,
        listener: DaysAdapterListener
    ) -> Unit
        get() = { viewHolder, day, properties, listener ->
            if (checkAvailability(viewHolder, day, properties)) {
                onDayClicked(viewHolder, properties, day, rangeSelectionListener)
                listener.onDaysNotifyDataSetChanged()
            }
        }

    override fun dayBackground(
        viewHolder: DayViewHolder,
        day: Day,
        properties: CalendarProperties
    ) {
        if (checkAvailability(viewHolder, day, properties)) {
            viewHolder.bgDay.setBackgroundFromDrawable(
                getDayBackground(
                    currentDay = day,
                    checkIn = properties.selectedCheckIn,
                    checkOut = properties.selectedCheckOut
                )
            )
        }
    }

    override fun isDaySelected(
        currentDay: Day,
        properties: CalendarProperties
    ): Boolean {
        properties.apply {
            if (selectedCheckIn != null && selectedCheckOut != null)
                return currentDay >= selectedCheckIn!! && currentDay <= selectedCheckOut!!

            if (selectedCheckIn != null)
                return currentDay == selectedCheckIn

            if (selectedCheckOut != null)
                return currentDay == selectedCheckOut
        }

        return false
    }

    private fun getDayBackground(
        currentDay: Day,
        checkIn: Day?,
        checkOut: Day?,
    ): Int {
        return if (checkIn != null && checkOut != null) {
            when {
                checkIn == currentDay -> R.drawable.bg_day_range_start
                checkOut == currentDay -> R.drawable.bg_day_range_end
                else -> if (checkIn < currentDay && checkOut > currentDay)
                    R.drawable.bg_day_selected
                else R.drawable.bg_day
            }
        } else {
            if (checkIn != null && checkIn == currentDay)
                R.drawable.bg_day_range_start
            else R.drawable.bg_day
        }
    }

    private fun onDayClicked(
        viewHolder: DayViewHolder,
        property: CalendarProperties,
        currentDay: Day,
        listener: RangeSelectionListener
    ) {
        property.apply {
            when {
                selectedCheckIn == currentDay -> {
                    selectedCheckIn = null
                    selectedCheckOut = null
                }
                isCheckOutSelect() && currentDay == selectedCheckOut && currentDay.isDisable -> {
                    return
                }
                selectedCheckIn == currentDay || selectedCheckIn == null || isCheckOutSelect() -> {
                    selectedCheckIn = currentDay
                    selectedCheckOut = null
                    listener.onCheckInSelected(selectedCheckIn!!)
                    if (showBubbleMessage)
                        createToolTip(
                            viewHolder.itemView,
                            createMinNightText(minDaysInRangeSelection, viewHolder.context)
                        )
                }
                else -> {
                    if (selectedCheckIn!! > currentDay) {
                        selectedCheckOut = selectedCheckIn
                        selectedCheckIn = currentDay
                        listener.onCheckInSelected(selectedCheckIn!!)
                        listener.onCheckOutSelected(selectedCheckOut!!)
                    } else {
                        selectedCheckOut = currentDay
                        listener.onCheckOutSelected(selectedCheckOut!!)
                    }
                    if (showBubbleMessage)
                        createToolTip(
                            viewHolder.itemView,
                            getCountText(getSelectedDays(property), viewHolder.context)
                        )
                }
            }
        }
    }

    private fun getSelectedDays(property: CalendarProperties): Int {
        return if (property.regionalType == RegionalType.Jalali)
            DateUtil.diffDaysJalali(
                property.selectedCheckIn ?: return 0,
                property.selectedCheckOut ?: return 0
            )[1].toInt()
        else
            DateUtil.diffDaysGregorian(
                property.selectedCheckIn ?: return 0,
                property.selectedCheckOut ?: return 0
            )[1].toInt()
    }

    private fun createToolTip(view: View, text: CharSequence) {
        val displayWidth = view.context.resources.displayMetrics.widthPixels
        val displayHeight = view.context.resources.displayMetrics.heightPixels

        view.doOnLayout {
            val parent = view.parent as ViewGroup
            val bubbleLayout = LayoutInflater
                .from(view.context)
                .inflate(R.layout.date_tip_view, parent, false) as BubbleLayout
            bubbleLayout.findViewById<TextView>(R.id.tv_text).text = text
            val location = IntArray(2)
            view.getLocationInWindow(location)
            val x = location[0]
            val y = location[1]
            val popupWindow = BubblePopupHelper.create(view.context, bubbleLayout)
            val magicNumber = (view.width / 2).toFloat() - view.dp(4)

            if (x < parent.width / 2) {
                bubbleLayout.arrowPosition = magicNumber
                popupWindow.showAtLocation(
                    view,
                    Gravity.START or Gravity.TOP,
                    x,
                    y - 110
                )
            } else {
                bubbleLayout.arrowPosition = bubbleLayout.apply {
                    measure(
                        View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
                        View.MeasureSpec.makeMeasureSpec(
                            (parent as? View)?.width ?: 100,
                            View.MeasureSpec.AT_MOST
                        )
                    )
                }.measuredWidth - magicNumber
                popupWindow.showAtLocation(
                    view,
                    Gravity.END or Gravity.TOP,
                    displayWidth - (x + view.width),
                    y - 110
                )
            }
        }
    }

    private fun createMinNightText(minNight: Int, context: Context): CharSequence {
        val spannableStringBuilder = SpannableStringBuilder()
        val s1 = SpannableString("حداقل شب رزرو:")
            .apply {
//                setSpan(TypefaceSpan(context, R.font.iran_yekan), 0, length, 0)
                setSpan(
                    ForegroundColorSpan(ContextCompat.getColor(context, R.color.secondary)),
                    0,
                    length,
                    0
                )
                setSpan(
                    12,
                    0,
                    length,
                    0
                )
            }
        val s2 = SpannableString("$minNight شب")
            .apply {
//                setSpan(TypefaceSpan(context, R.font.iran_yekan_bold), 0, length, 0)
                setSpan(
                    ForegroundColorSpan(ContextCompat.getColor(context, R.color.secondary)),
                    0,
                    length,
                    0
                )
                setSpan(
                    14,
                    0,
                    length,
                    0
                )
            }
        spannableStringBuilder
            .append(s1)
            .append(" ")
            .append(s2)

        return spannableStringBuilder
    }

    private fun getCountText(count: Int, context: Context): CharSequence {
        return SpannableString("$count شب اقامت")
            .apply {
//                setSpan(TypefaceSpan(context, R.font.iran_yekan_bold), 0, length, 0)
                setSpan(
                    ForegroundColorSpan(ContextCompat.getColor(context, R.color.secondary)),
                    0,
                    length,
                    0
                )
                setSpan(
                    14,
                    0,
                    length,
                    0
                )
            }
    }
}
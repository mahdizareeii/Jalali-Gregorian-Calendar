package com.calendar.base.calendar

import com.calendar.base.model.MonthItem
import java.util.*

class MyGregorianCalendar : BaseCalendar() {

    private val calendar = GregorianCalendar()

    override val nameOfMonths: List<String>
        get() = listOf(
            "January", "February", "March", "April", "May", "June",
            "July", "August", "September", "October", "November", "December"
        )

    override fun getDisplayedMonthName(value: Int): String {
        return nameOfMonths.getOrNull(value) ?: "Unknown"
    }

    override fun firstDayPositionInWeek(): Int {
        calendar.set(Calendar.DAY_OF_MONTH, 1)
        val position = calendar.get(Calendar.DAY_OF_WEEK)
        return if (position == 7) 0 else position
    }

    override fun generateDays(month: Int): List<Int> {
        val days = ArrayList<Int>()
        calendar.set(Calendar.MONTH, month)
        val countOfDays = calendar.getActualMaximum(Calendar.DAY_OF_MONTH)

        //shift days
        repeat(firstDayPositionInWeek()) {
            days.add(-1)
        }

        for (day in 1..countOfDays) {
            days.add(day)
        }

        return days
    }

    override fun getYearName(): String = calendar.get(Calendar.YEAR).toString()

    override fun getNextDates(field: Int, value: Int): List<MonthItem> {
        val months = ArrayList<MonthItem>()
        val today = Calendar.getInstance()
        val next = Calendar.getInstance().apply {
            set(field, get(field) + value)
        }

        val todayYear = today.get(Calendar.YEAR)
        val todayMonth = today.get(Calendar.MONTH)
        val nextYear = next.get(Calendar.YEAR)
        val nextMonth = next.get(Calendar.MONTH)

        if (todayYear == nextYear) {
            for (month in todayMonth until nextMonth) {
                months.add(MonthItem(MyGregorianCalendar(), month, todayYear))
            }
        } else {
            if (field == Calendar.YEAR)
                for (year in todayYear..nextYear) {
                    for (month in 0..11)
                        months.add(MonthItem(MyGregorianCalendar(), month, year))
                }
            else if (field == Calendar.MONTH) {
                var temp = value
                var tempYear = todayYear
                while (temp >= 12) {
                    for (month in 0..11)
                        months.add(MonthItem(MyGregorianCalendar(), month, tempYear))
                    temp -= 12
                    tempYear++
                }

                if (temp != 0)
                    for (month in 0..temp)
                        months.add(MonthItem(MyGregorianCalendar(), month, tempYear))
            }
        }
        return months
    }

    override fun set(field: Int, value: Int) {
        calendar.set(field, value)
    }


}
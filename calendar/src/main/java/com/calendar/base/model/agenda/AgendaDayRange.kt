package com.calendar.base.model.agenda

import com.calendar.base.model.DayRange

class AgendaDayRange(
    id: String? = null,
    title: String? = null,
    color: String? = null,
    val agendaRangeList: List<DayRange> = listOf()
) : Agenda(id, title, color)
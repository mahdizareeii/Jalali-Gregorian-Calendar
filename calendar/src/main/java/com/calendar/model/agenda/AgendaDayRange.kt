package com.calendar.model.agenda

import com.calendar.model.DayRange

class AgendaDayRange(
    id: String? = null,
    title: String? = null,
    color: String? = null,
    val agendaRangeList: List<DayRange> = listOf()
) : Agenda(id, title, color)
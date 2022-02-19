package com.calendar.base.model.agenda

import com.calendar.base.model.Day

class AgendaDays(
    id: String? = null,
    title: String? = null,
    color: String? = null,
    val agendaList: List<Day> = listOf(),
) : Agenda(id, title, color)

package com.calendar.model.agenda

import com.calendar.model.Day

class AgendaDays(
    id: String? = null,
    title: String? = null,
    color: String? = null,
    val agendaList: List<Day> = listOf(),
) : Agenda(id, title, color)

package com.calendar.base.model.agenda

import android.graphics.Color

abstract class Agenda(
    val id: String? = null,
    val title: String? = null,
    val color: String? = null,
){
    fun getAgendaColor() = try {
        Color.parseColor(color)
    } catch (e: IllegalArgumentException) {
        Color.parseColor("#ffa500")
    }
}
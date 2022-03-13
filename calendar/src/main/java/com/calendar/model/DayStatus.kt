package com.calendar.model

enum class DayStatus(val status: String) {
    AVAILABLE("available"),
    UN_AVAILABLE("unavailable"),
    DISABLE("disabled"),
    RESERVED("reserved");

    companion object {
        fun fromValue(value: String?): DayStatus =
            values().firstOrNull { it.status.equals(value, true) } ?: UN_AVAILABLE
    }
}
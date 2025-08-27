package com.roys.wolvnotekmp.common

import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.format
import kotlinx.datetime.format.FormatStringsInDatetimeFormats
import kotlinx.datetime.format.byUnicodePattern
import kotlinx.datetime.toLocalDateTime
import kotlin.time.Clock
import kotlin.time.ExperimentalTime

object DateTimeHelper {

    @OptIn(FormatStringsInDatetimeFormats::class)
    private val formatter = LocalDateTime.Format {
        byUnicodePattern("dd MM yyyy HH:mm")
    }

    @OptIn(ExperimentalTime::class)
    fun getCurrentDateTime(): String {
        val now = Clock.System
            .now()
            .toLocalDateTime(TimeZone.currentSystemDefault())
        return now.format(formatter)
    }

    @OptIn(ExperimentalTime::class)
    fun getId(): String{
        val now = Clock.System.now()
        return now.toEpochMilliseconds().toString()
    }

    fun getTimeZone(): String{
        val timeZone: TimeZone = TimeZone.currentSystemDefault()
        return timeZone.id
    }
}
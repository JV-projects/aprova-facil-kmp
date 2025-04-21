package edu.fatec.jvprojects.utils.extensions

import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime


fun Long.convertMilisToDate() : String {
    val instant = Instant.fromEpochMilliseconds(this)

    val data = instant.toLocalDateTime(TimeZone.UTC).date.toString()


    return data.split("-").reversed().joinToString("/")
}
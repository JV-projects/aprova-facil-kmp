package edu.fatec.jvprojects.model

import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable

@Serializable
data class ApiError(
    val timestamp: LocalDateTime,
    val status: Int,
    val mensagem: String
)
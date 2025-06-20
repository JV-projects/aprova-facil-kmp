package edu.fatec.jvprojects.model

import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Polymorphic
import kotlinx.serialization.Serializable

@Serializable
sealed class BaseResponseEntity {
    abstract val timestamp: LocalDateTime
    abstract val status: Int
    abstract val mensagem: String
}
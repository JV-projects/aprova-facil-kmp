package edu.fatec.jvprojects.model

import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable

@Serializable
class ErrorResponseEntity(
    override val timestamp: LocalDateTime,
    override val status: Int,
    override val mensagem: String
): BaseResponseEntity()
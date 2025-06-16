package edu.fatec.jvprojects.model.dtos

import kotlinx.serialization.Serializable

@Serializable
data class ClienteDevolutivaDTO(
    val id: Long,
    val nome: String,
    val email: String,
    val status: String
)
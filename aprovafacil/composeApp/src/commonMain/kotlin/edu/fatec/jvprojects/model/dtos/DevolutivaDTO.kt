package edu.fatec.jvprojects.model.dtos

import kotlinx.serialization.Serializable

@Serializable
data class DevolutivaDTO(
    val idCliente: Long,
    val devolutiva: String
)
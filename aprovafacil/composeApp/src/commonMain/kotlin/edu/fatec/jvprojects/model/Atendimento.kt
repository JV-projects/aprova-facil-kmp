package edu.fatec.jvprojects.model

import kotlinx.serialization.Serializable

@Serializable
data class Atendimento(
    val idCliente: Long,
    val emailCorretor: String,
    val analiseCredito: String
)
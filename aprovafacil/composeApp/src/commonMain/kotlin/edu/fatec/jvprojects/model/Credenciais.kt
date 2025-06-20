package edu.fatec.jvprojects.model

import kotlinx.serialization.Serializable

@Serializable
data class Credenciais(
    val email: String,
    val senha: String
)
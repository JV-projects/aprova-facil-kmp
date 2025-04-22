package edu.fatec.jvprojects.model

import edu.fatec.jvprojects.model.enums.TipoRenda
import kotlinx.serialization.Serializable

@Serializable
data class PerfilFinanceiro(
    val rendaBruta: Double,
    val tipoRenda: TipoRenda,
    val possuiRestricao: Boolean,
    val possuiDependente: Boolean? = false
)
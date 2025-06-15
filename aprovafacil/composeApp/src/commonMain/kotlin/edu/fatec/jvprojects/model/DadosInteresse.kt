package edu.fatec.jvprojects.model

import edu.fatec.jvprojects.model.enums.EstadoImovel
import edu.fatec.jvprojects.model.enums.TipoImovel
import kotlinx.serialization.Serializable

@Serializable
data class DadosInteresse(
    val tipoImovel: TipoImovel,
    val estadoImovel: EstadoImovel
)

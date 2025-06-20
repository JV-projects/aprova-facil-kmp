package edu.fatec.jvprojects.model

import edu.fatec.jvprojects.model.enums.TipoDocumento
import kotlinx.serialization.Serializable

@Serializable
data class DocumentoCliente(
    val tipo: TipoDocumento,
    val nomeArquivo: String
)

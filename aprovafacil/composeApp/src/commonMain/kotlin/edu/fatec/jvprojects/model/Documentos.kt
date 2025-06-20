package edu.fatec.jvprojects.model

import io.github.vinceglb.filekit.PlatformFile
import kotlinx.serialization.Serializable

@Serializable
class Documentos(
    val rg: String,
    val cpf: String,
    val comprovanteResidencia: String,
    val pisCartaoCidadao: String,
    val certidao: String,
    val holerite: String,
    val extratoFgts: String,
    val declaracaoIr: String,
    val carteiraTrabalho: String
)
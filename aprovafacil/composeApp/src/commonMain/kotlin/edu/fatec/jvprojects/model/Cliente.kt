package edu.fatec.jvprojects.model

import edu.fatec.jvprojects.model.enums.EstadoCivil
import edu.fatec.jvprojects.model.enums.TipoDocumento
import kotlinx.datetime.LocalDate
import kotlinx.serialization.Serializable

@Serializable
data class Cliente(
    val id : Long? = null,
    val nome: String,
    val cpf: String,
    val telefone: String,
    val email: String,
    val statusCadastro: String = "PENDENTE",
    val dataNascimento: LocalDate,
    val estadoCivil: EstadoCivil,
    val perfilFinanceiro: PerfilFinanceiro,
    val dadosInteresse: DadosInteresse,
    val participante: String?,
    val devolutiva: String? = null,
    val documentos: Map<TipoDocumento, String>,
)

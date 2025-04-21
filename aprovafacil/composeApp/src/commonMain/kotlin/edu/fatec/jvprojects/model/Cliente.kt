package edu.fatec.jvprojects.model

import edu.fatec.jvprojects.model.enums.TipoRenda
import kotlinx.datetime.LocalDate
import kotlinx.serialization.Serializable

@Serializable
data class Cliente(
    val nome: String,
    val cpf: String,
    val telefone: String,
    val email: String,
    val status: String = "PENDENTE",
    val dataNascimento: LocalDate,
    val perfilFinanceiro: PerfilFinanceiro,
    val dadosInteresse: DadosInteresse
)

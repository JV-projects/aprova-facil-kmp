package edu.fatec.jvprojects.wrapper

import edu.fatec.jvprojects.model.Cliente

sealed class ResultadoBusca {
    data class Sucesso(val cliente: Cliente) : ResultadoBusca()
    data class Erro(val mensagem: String) : ResultadoBusca()
    object Carregando : ResultadoBusca()
}
package edu.fatec.jvprojects.wrapper

import edu.fatec.jvprojects.model.ApiError

sealed class Resultado<out T> {
    data class Sucesso<T>(val data: T) : Resultado<T>()
    data class Erro(val error: ApiError) : Resultado<Nothing>()
}
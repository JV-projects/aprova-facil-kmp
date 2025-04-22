package edu.fatec.jvprojects.repository

import edu.fatec.jvprojects.configuration.createHttpClient
import edu.fatec.jvprojects.model.Cliente
import edu.fatec.jvprojects.wrapper.ResultadoBusca
import io.ktor.client.call.body
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.http.isSuccess

class ClienteRepository {

    private val httpClient = createHttpClient()

    private val URL = "http://localhost:8080/clientes"

    suspend fun salvarCliente(cliente: Cliente) {
        val response: HttpResponse = httpClient.post("$URL/salvar") {
            contentType(ContentType.Application.Json)
            setBody(cliente)
        }
    }

    suspend fun buscarPorCpf(cpf: String): ResultadoBusca {
        return try {
            val response: HttpResponse = httpClient.post("$URL/buscar") {
                contentType(ContentType.Application.Json)
                setBody(mapOf("cpf" to cpf))
            }
            if (response.status.isSuccess()) {
                val cliente = response.body<Cliente>()
                ResultadoBusca.Sucesso(cliente)
            } else {
                val erroTexto = response.bodyAsText()
                ResultadoBusca.Erro("Erro: $erroTexto")
            }

        } catch (e: Exception) {
            ResultadoBusca.Erro("Erro inesperado: ${e.message}")
        }
    }
}
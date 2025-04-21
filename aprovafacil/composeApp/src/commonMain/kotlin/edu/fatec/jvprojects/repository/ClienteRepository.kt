package edu.fatec.jvprojects.repository

import edu.fatec.jvprojects.configuration.createHttpClient
import edu.fatec.jvprojects.model.Cliente
import io.ktor.client.request.post
import io.ktor.client.request.request
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import io.ktor.http.ContentType
import io.ktor.http.HttpMethod
import io.ktor.http.contentType

class ClienteRepository {

    private val httpClient = createHttpClient()

    private val URL = "http://localhost:8080/clientes"

    suspend fun salvarCliente(cliente: Cliente) {
        val response: HttpResponse = httpClient.post("$URL/salvar") {
            contentType(ContentType.Application.Json)
            setBody(cliente)
        }
    }
}
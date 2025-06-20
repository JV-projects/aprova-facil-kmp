package edu.fatec.jvprojects.repository

import edu.fatec.jvprojects.configuration.createHttpClient
import edu.fatec.jvprojects.model.ApiError
import edu.fatec.jvprojects.model.Atendimento
import edu.fatec.jvprojects.model.Cliente
import edu.fatec.jvprojects.model.Credenciais
import edu.fatec.jvprojects.model.enums.StatusCliente
import edu.fatec.jvprojects.wrapper.Resultado
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.http.isSuccess
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

class AdministradorRepository {

    private val httpClient = createHttpClient()

    private val LOGIN = "http://localhost:8080/auth/login"

    private val URL = "http://localhost:8080/administrador"

    suspend fun login(credenciais: Credenciais): Resultado<Map<String, String>> {
        return try {
            val response: HttpResponse = httpClient.post(LOGIN) {
                contentType(ContentType.Application.Json)
                setBody(credenciais)
            }

            if (response.status.isSuccess()) {
                val token = response.body<Map<String, String>>()
                Resultado.Sucesso(token)
            } else {
                val apiError = response.body<ApiError>()
                Resultado.Erro(apiError)
            }
        } catch (e: Exception) {
            Resultado.Erro(
                ApiError(
                    Clock.System.now().toLocalDateTime(TimeZone.UTC),
                    0,
                    e.message ?: "Erro inesperado"
                )
            )
        }
    }

    suspend fun listarClientes(): Resultado<List<Cliente>> {
        return try {
            val response: HttpResponse = httpClient.get("http://localhost:8080/clientes/listar")

            if (response.status.isSuccess()) {
                val clientes = response.body<List<Cliente>>()
                Resultado.Sucesso(clientes)
            } else {
                val apiError = response.body<ApiError>()
                Resultado.Erro(apiError)
            }
        } catch (e: Exception) {
            Resultado.Erro(
                ApiError(
                    Clock.System.now().toLocalDateTime(TimeZone.UTC),
                    0,
                    e.message ?: "Erro inesperado"
                )
            )
        }
    }

    suspend fun buscarPorStatus(status: StatusCliente): Resultado<List<Cliente>> {
        return try {
            val response: HttpResponse =
                httpClient.get("http://localhost:8080/clientes/buscarPorStatus") {
                    parameter("status", status)
                }
            if (response.status.isSuccess()) {
                val clientes = response.body<List<Cliente>>()
                Resultado.Sucesso(clientes)
            } else {
                val apiError = response.body<ApiError>()
                Resultado.Erro(apiError)
            }
        } catch (e: Exception) {
            Resultado.Erro(
                ApiError(
                    Clock.System.now().toLocalDateTime(TimeZone.UTC),
                    0,
                    e.message ?: "Erro inesperado"
                )
            )
        }
    }

    suspend fun distribuirCliente(atendimento: Atendimento, token: String): Resultado<Map<String, String>> {
        return try {
            val response: HttpResponse = httpClient.post("$URL/distribuir") {
                contentType(ContentType.Application.Json)
                header("Authorization", "Bearer $token")
                setBody(atendimento)
            }

            if (response.status.isSuccess()) {
                val codigoDevolutiva = response.body<Map<String, String>>()
                Resultado.Sucesso(codigoDevolutiva)
            } else {
                val apiError = response.body<ApiError>()
                Resultado.Erro(apiError)
            }
        } catch (e: Exception) {
            Resultado.Erro(
                ApiError(
                    Clock.System.now().toLocalDateTime(TimeZone.UTC),
                    0,
                    e.message ?: "Erro inesperado"
                )
            )
        }
    }
}
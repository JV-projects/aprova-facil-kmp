package edu.fatec.jvprojects.repository

import edu.fatec.jvprojects.configuration.createHttpClient
import edu.fatec.jvprojects.model.ApiError
import edu.fatec.jvprojects.model.dtos.ClienteDevolutivaDTO
import edu.fatec.jvprojects.model.dtos.DevolutivaDTO
import edu.fatec.jvprojects.wrapper.Resultado
import io.ktor.client.call.body
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import io.ktor.client.utils.EmptyContent.contentType
import io.ktor.http.ContentType
import io.ktor.http.isSuccess
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import io.ktor.http.*

class DevolutivaRepository {
    private val httpClient = createHttpClient()

    private val baseUrl = "http://localhost:8080/corretor"

    suspend fun validarCodigoDevolutiva(codigoDevolutiva: String): Resultado<ClienteDevolutivaDTO> {
        return try {
            val response: HttpResponse = httpClient.post("$baseUrl/validarCodigoDevolutiva") {
                parameter("codigoDevolutiva", codigoDevolutiva)
            }.body()

            if (response.status.isSuccess()) {
                Resultado.Sucesso(response.body<ClienteDevolutivaDTO>())
            } else {
                val apiError = response.body<ApiError>()
                Resultado.Erro(apiError)
            }
        }catch (e: Exception) {
            return Resultado.Erro(ApiError(
                Clock.System.now().toLocalDateTime(TimeZone.UTC),
                0,
                e.message ?: "Erro inesperado"
            ))
        }
    }

    suspend fun registrarDevolutiva(devolutiva: DevolutivaDTO) : Resultado<String> {
        return try {
            val response: HttpResponse = httpClient.post("$baseUrl/devolutiva") {
                contentType(ContentType.Application.Json)
                setBody(devolutiva)
            }
            if (response.status.isSuccess()) {
                Resultado.Sucesso("Devolutiva cadastrada com sucesso.")
            } else {
                val apiError = response.body<ApiError>()
                Resultado.Erro(apiError)
            }
        }catch (e: Exception) {
            return Resultado.Erro(ApiError(
                Clock.System.now().toLocalDateTime(TimeZone.UTC),
                0,
                e.message ?: "Erro inesperado"
            ))
        }
    }
}
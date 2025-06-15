package edu.fatec.jvprojects.repository

import aprovafacil.composeapp.generated.resources.Res
import edu.fatec.jvprojects.configuration.createHttpClient
import edu.fatec.jvprojects.model.ApiError
import edu.fatec.jvprojects.model.Cliente
import edu.fatec.jvprojects.model.Documentos
import edu.fatec.jvprojects.wrapper.Resultado
import io.github.vinceglb.filekit.readBytes
import io.ktor.client.call.body
import io.ktor.client.plugins.observer.ResponseObserver
import io.ktor.client.plugins.onUpload
import io.ktor.client.request.delete
import io.ktor.client.request.forms.MultiPartFormDataContent
import io.ktor.client.request.forms.formData
import io.ktor.client.request.post
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.request
import io.ktor.http.ContentType
import io.ktor.http.Headers
import io.ktor.http.HttpHeaders
import io.ktor.http.contentType
import io.ktor.http.isSuccess
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlinx.io.files.SystemFileSystem

class ClienteRepository {

    private val httpClient = createHttpClient()

    private val URL = "http://localhost:8080/clientes"

    suspend fun salvarCliente(cliente: Cliente): Resultado<Long> {
        return try {
            val response: HttpResponse = httpClient.post("$URL/salvar") {
                contentType(ContentType.Application.Json)
                setBody(cliente)
            }

            if (response.status.isSuccess()) {
                val cliente = response.body<Cliente>()
                Resultado.Sucesso(cliente.id!!)
            } else {
                val apiError = response.body<ApiError>()
                Resultado.Erro(apiError)
            }
        } catch(e: Exception) {
            return Resultado.Erro(ApiError(
                Clock.System.now().toLocalDateTime(TimeZone.UTC),
                0,
                e.message ?: "Erro inesperado"
            ))
        }
    }

    suspend fun buscarPorCpf(cpf: String): Resultado<Cliente> {
        return try {
            val response: HttpResponse = httpClient.post("$URL/buscarPorCpf") {
                contentType(ContentType.Application.Json)
                setBody(mapOf("cpf" to cpf))

            }
            if (response.status.isSuccess()) {
                val cliente = response.body<Cliente>()
                Resultado.Sucesso(cliente)
            } else {
                val apiError = response.body<ApiError>()
                Resultado.Erro(apiError)
            }

        } catch (e: Exception) {
            return Resultado.Erro(ApiError(
                Clock.System.now().toLocalDateTime(TimeZone.UTC),
                0,
                e.message ?: "Erro inesperado"
            ))
        }
    }

    suspend fun atualizarCliente(cliente: Cliente): Resultado<Long> {
        return try {
            val response: HttpResponse = httpClient.put("$URL/atualizar") {
                contentType(ContentType.Application.Json)
                setBody(cliente)
            }

            if (response.status.isSuccess()) {
                val cliente = response.body<Cliente>()
                Resultado.Sucesso(cliente.id!!)
            } else {
                val apiError = response.body<ApiError>()
                Resultado.Erro(apiError)
            }
        } catch (e: Exception) {
            return Resultado.Erro(ApiError(
                Clock.System.now().toLocalDateTime(TimeZone.UTC),
                0,
                e.message ?: "Erro inesperado"
            ))
        }
    }

    suspend fun deletarCliente(id: Long): Resultado<String> {
        return try {
            val response: HttpResponse = httpClient.delete("$URL/deletar/$id")

            if (response.status.isSuccess()) {
                Resultado.Sucesso("Cliente deletado com sucesso")
            } else {
                val apiError = response.body<ApiError>()
                Resultado.Erro(apiError)
            }
        } catch (e: Exception) {
            return Resultado.Erro(ApiError(
                Clock.System.now().toLocalDateTime(TimeZone.UTC),
                0,
                e.message ?: "Erro inesperado"
            ))
        }
    }


}

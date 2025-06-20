package edu.fatec.jvprojects.repository

import edu.fatec.jvprojects.configuration.createHttpClient
import edu.fatec.jvprojects.model.ApiError
import edu.fatec.jvprojects.model.Documentos
import edu.fatec.jvprojects.wrapper.Resultado
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.forms.MultiPartFormDataContent
import io.ktor.client.request.forms.formData
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import io.ktor.http.Headers
import io.ktor.http.HttpHeaders
import io.ktor.http.isSuccess
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

class DocumentosRepository {
    private val httpClient = createHttpClient()

    private val URL = "http://localhost:8080/clientes"

    suspend fun enviarDocumentos(documentos: Documentos) : Resultado<String> {
        return try {
            val response: HttpResponse = httpClient.post("$URL/documentos") {
                setBody(MultiPartFormDataContent(
                    formData {
                        append("clienteId", 0)
                        append("RG", documentos.rg, Headers.build {
                            append(HttpHeaders.ContentType, "application/pdf")
                            append(HttpHeaders.ContentDisposition, "filename=\"docRg\"")
                        })
                        append("CPF", documentos.cpf, Headers.build {
                            append(HttpHeaders.ContentType, "application/pdf")
                            append(HttpHeaders.ContentDisposition, "filename=\"docCpf\"")
                        })
                        append("COMPROVANTE_RESIDENCIA", documentos.comprovanteResidencia, Headers.build {
                            append(HttpHeaders.ContentType, "application/pdf")
                            append(HttpHeaders.ContentDisposition, "filename=\"docResidencia\"")
                        })
                        append("PIS_CARTAO_CIDADAO", documentos.pisCartaoCidadao, Headers.build {
                            append(HttpHeaders.ContentType, "application/pdf")
                            append(HttpHeaders.ContentDisposition, "filename=\"docPisCartao\"")
                        })
                        append("CERTIDAO", documentos.certidao, Headers.build {
                            append(HttpHeaders.ContentType, "application/pdf")
                            append(HttpHeaders.ContentDisposition, "filename=\"docCertidao\"")
                        })
                        append("HOLERITE", documentos.holerite, Headers.build {
                            append(HttpHeaders.ContentType, "application/pdf")
                            append(HttpHeaders.ContentDisposition, "filename=\"docHolerite\"")
                        })
                        append("EXTRATO_FGTS", documentos.extratoFgts, Headers.build {
                            append(HttpHeaders.ContentType, "application/pdf")
                            append(HttpHeaders.ContentDisposition, "filename=\"docExtrato\"")
                        })
                        append("DECLARACAO_IR", documentos.declaracaoIr, Headers.build {
                            append(HttpHeaders.ContentType, "application/pdf")
                            append(HttpHeaders.ContentDisposition, "filename=\"docIr\"")
                        })
                        append("CARTEIRA_TRABALHO", documentos.carteiraTrabalho, Headers.build {
                            append(HttpHeaders.ContentType, "application/pdf")
                            append(HttpHeaders.ContentDisposition, "filename=\"docCarteira\"")
                        })
                    },
                    boundary = "WebAppBoundary"
                ))
            }

            if (response.status.isSuccess()) {
                Resultado.Sucesso(response.body<String>())
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
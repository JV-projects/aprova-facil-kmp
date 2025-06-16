package edu.fatec.jvprojects.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import edu.fatec.jvprojects.model.Cliente
import edu.fatec.jvprojects.model.DadosInteresse
import edu.fatec.jvprojects.model.PerfilFinanceiro
import edu.fatec.jvprojects.model.enums.EstadoCivil
import edu.fatec.jvprojects.model.enums.EstadoImovel
import edu.fatec.jvprojects.model.enums.StatusCliente
import edu.fatec.jvprojects.model.enums.TipoDocumento
import edu.fatec.jvprojects.model.enums.TipoImovel
import edu.fatec.jvprojects.model.enums.TipoRenda
import edu.fatec.jvprojects.repository.ClienteRepository
import edu.fatec.jvprojects.wrapper.Resultado
import io.github.vinceglb.filekit.PlatformFile
import io.github.vinceglb.filekit.readBytes
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.atStartOfDayIn
import kotlinx.datetime.toLocalDateTime
import kotlin.io.encoding.Base64
import kotlin.io.encoding.ExperimentalEncodingApi
import kotlin.reflect.KClass
import kotlin.time.Clock
import kotlin.time.ExperimentalTime

data class ClienteFormUiState @OptIn(ExperimentalTime::class) constructor(
    val id: Long? = null,
    val nomeCompleto: String = "",
    val cpf: String = "",
    val celular: String = "",
    val email: String = "",
    var dataNascimentoMillis: Long = Clock.System.now().toEpochMilliseconds(),
    val estadoCivil: EstadoCivil = EstadoCivil.SOLTEIRO, // Assumindo enum
    val rendaBruta: String = "0", // Manter como String para entrada do usuário, converter ao salvar
    val tipoRenda: TipoRenda = TipoRenda.FORMAL, // Assumindo enum
    val possuiRestricao: Boolean = false,
    val possuiDependente: Boolean = false,
    val tresAnosFgts: Boolean = false,
    val usarFgts: Boolean = false,
    val tipoImovel: TipoImovel = TipoImovel.CASA, // Assumindo enum
    val estadoImovel: EstadoImovel = EstadoImovel.NOVO, // Assumindo enum
    val participante: String = "",
    val docRg: PlatformFile? = null,
    val docCpf: PlatformFile? = null,
    val comprovanteResidencia: PlatformFile? = null,
    val pisCartaoCidadao: PlatformFile? = null,
    val certidao: PlatformFile? = null,
    val holerite: PlatformFile? = null,
    val extratoFgts: PlatformFile? = null,
    val declaracaoIr: PlatformFile? = null,
    val carteiraTrabalho: PlatformFile? = null,

    val pathRg: String? = null,
    val pathCpf: String? = null,
    val pathComprovanteResidencia: String? = null,
    val pathPisCartaoCidadao: String? = null,
    val pathCertidao: String? = null,
    val pathHolerite: String? = null,
    val pathExtratoFgts: String? = null,
    val pathDeclaracaoIr: String? = null,
    val pathCarteiraTrabalho: String? = null,

    val fromTela: String? = null,

    // Estado da operação de salvar
    val isSaving: Boolean = false,
    val saveError: String? = null,
    val saveSuccessClienteId: Long? = null,

    val isLoadingCliente: Boolean = false, // Para feedback ao buscar
    val clienteNaoEncontradoError: String? = null
)

sealed interface ClienteFormEvent {
    data class ClienteSalvoComSucesso(val clienteId: Long) : ClienteFormEvent
    data class ErroAoSalvar(val mensagem: String) : ClienteFormEvent
    data class ClienteCarregadoComSucesso(val tela: String) : ClienteFormEvent
    data class ErroAoCarregarCliente(val mensagem: String) : ClienteFormEvent
    object DeletadoComSucesso : ClienteFormEvent
}

class ClienteViewModel(
    private val clienteRepository: ClienteRepository = ClienteRepository()
) : ViewModel() {
    private val _uiState = MutableStateFlow(ClienteFormUiState())
    val uiState: StateFlow<ClienteFormUiState> = _uiState

    private val _formEvent = MutableSharedFlow<ClienteFormEvent>()
    val formEvent = _formEvent.asSharedFlow()

    fun onNomeCompletoChange(novoNome: String) {
        _uiState.update { it.copy(nomeCompleto = novoNome, saveError = null, saveSuccessClienteId = null) }
    }

    fun onCpfChange(novoCpf: String) {
        println(novoCpf)
        _uiState.update { it.copy(cpf = novoCpf, saveError = null, saveSuccessClienteId = null) }
    }

    fun onCelularChange(novoCelular: String) {
        _uiState.update { it.copy(celular = novoCelular, saveError = null, saveSuccessClienteId = null) }
    }

    fun onEmailChange(novoEmail: String) {
        _uiState.update { it.copy(email = novoEmail, saveError = null, saveSuccessClienteId = null) }
    }

    fun onDataNascimentoChange(novaDataMillis: Long) {
        _uiState.update { it.copy(dataNascimentoMillis = novaDataMillis, saveError = null, saveSuccessClienteId = null) }
    }

    fun onEstadoCivilChange(novoEstadoCivil: EstadoCivil) {
        _uiState.update { it.copy(estadoCivil = novoEstadoCivil, saveError = null, saveSuccessClienteId = null) }
    }

    fun onRendaBrutaChange(novaRenda: String) {
        _uiState.update { it.copy(rendaBruta = novaRenda, saveError = null, saveSuccessClienteId = null) }
    }

    fun onTipoRendaChange(novoTipoRenda: TipoRenda) {
        _uiState.update { it.copy(tipoRenda = novoTipoRenda, saveError = null, saveSuccessClienteId = null) }
    }

    fun onPossuiRestricaoChange(possui: Boolean) {
        _uiState.update { it.copy(possuiRestricao = possui, saveError = null, saveSuccessClienteId = null) }
    }

    fun onPossuiDependentesChange(possui: Boolean) {
        _uiState.update { it.copy(possuiDependente = possui, saveError = null, saveSuccessClienteId = null) }
    }

    fun onTresAnosFgtsChange(possui: Boolean) {
        _uiState.update { it.copy(tresAnosFgts = possui, saveError = null, saveSuccessClienteId = null) }
    }

    fun onUsarFgtsChange(usar: Boolean) {
        _uiState.update { it.copy(usarFgts = usar, saveError = null, saveSuccessClienteId = null) }
    }

    fun onTipoImovelChange(novoTipo: TipoImovel) {
        _uiState.update { it.copy(tipoImovel = novoTipo, saveError = null, saveSuccessClienteId = null) }
    }

    fun onEstadoImovelChange(novoEstado: EstadoImovel) {
        _uiState.update { it.copy(estadoImovel = novoEstado, saveError = null, saveSuccessClienteId = null) }
    }

    fun onParticipanteChange(novoParticipante: String) {
        _uiState.update { it.copy(participante = novoParticipante, saveError = null, saveSuccessClienteId = null) }
    }

    fun onRgChange(novoRg: PlatformFile?) {
        _uiState.update { it.copy(docRg = novoRg, isSaving = false, saveError = null) }
    }

    fun onCpfDocumentChanged(novoCpf: PlatformFile?) {
        _uiState.update { it.copy(docCpf = novoCpf, isSaving = false, saveError = null) }
    }

    fun onComprovanteResidenciaChange(novoComprovante: PlatformFile?) {
        _uiState.update {
            it.copy(
                comprovanteResidencia = novoComprovante,
                isSaving = false,
                saveError = null
            )
        }
    }

    fun onPisCartaoCidadaoChange(novoPis: PlatformFile?) {
        _uiState.update { it.copy(pisCartaoCidadao = novoPis, isSaving = false, saveError = null) }
    }

    fun onCertidaoChange(novaCertidao: PlatformFile?) {
        _uiState.update { it.copy(certidao = novaCertidao, isSaving = false, saveError = null) }
    }

    fun onHoleriteChange(novoHolerite: PlatformFile?) {
        _uiState.update { it.copy(holerite = novoHolerite, isSaving = false, saveError = null) }
    }

    fun onExtratoFgtsChange(novoExtrato: PlatformFile?) {
        _uiState.update { it.copy(extratoFgts = novoExtrato, isSaving = false, saveError = null) }
    }

    fun onDeclaracaoIrChange(novaDeclaracao: PlatformFile?) {
        _uiState.update {
            it.copy(
                declaracaoIr = novaDeclaracao,
                isSaving = false,
                saveError = null
            )
        }
    }

    fun onCarteiraTrabalhoChange(novaCarteira: PlatformFile?) {
        _uiState.update {
            it.copy(
                carteiraTrabalho = novaCarteira,
                isSaving = false,
                saveError = null
            )
        }
    }

    fun onTelaChange(tela: String) {
        _uiState.update { it.copy(fromTela = tela, isSaving = false, saveError = null) }
    }

    @OptIn(ExperimentalEncodingApi::class)
    fun salvarCliente() {
        viewModelScope.launch {
            _uiState.update { it.copy(isSaving = true, saveError = null, saveSuccessClienteId = null) }
            val currentState = _uiState.value // Pega o estado atual para construir o objeto Cliente

            val dadosInteresse = DadosInteresse(
                tipoImovel = currentState.tipoImovel,
                estadoImovel = currentState.estadoImovel
            )

            val perfilFinanceiro = PerfilFinanceiro(
                rendaBruta = currentState.rendaBruta.toDouble(),
                tipoRenda = currentState.tipoRenda,
                possuiRestricao = currentState.possuiRestricao,
                possuiDependente = currentState.possuiDependente,
                tresAnosFgts = currentState.tresAnosFgts,
                usarFgts = currentState.usarFgts
            )

            val map = mapOf<TipoDocumento, String>(
                TipoDocumento.RG to Base64.encode(currentState.docRg?.readBytes() ?: ByteArray(0)),
                TipoDocumento.CPF to Base64.encode(currentState.docCpf?.readBytes() ?: ByteArray(0)),
                TipoDocumento.COMPROVANTE_RESIDENCIA to Base64.encode(currentState.comprovanteResidencia?.readBytes() ?: ByteArray(0)),
                TipoDocumento.PIS_CARTAO_CIDADAO to Base64.encode(currentState.pisCartaoCidadao?.readBytes() ?: ByteArray(0)),
                TipoDocumento.CERTIDAO to Base64.encode(currentState.certidao?.readBytes() ?: ByteArray(0)),
                TipoDocumento.HOLERITE to Base64.encode(currentState.holerite?.readBytes() ?: ByteArray(0)),
                TipoDocumento.EXTRATO_FGTS to Base64.encode(currentState.extratoFgts?.readBytes() ?: ByteArray(0)),
                TipoDocumento.DECLARACAO_IR to Base64.encode(currentState.declaracaoIr?.readBytes() ?: ByteArray(0)),
                TipoDocumento.CARTEIRA_TRABALHO to Base64.encode(currentState.carteiraTrabalho?.readBytes() ?: ByteArray(0))
            )

            val cliente = Cliente(
                id = null, // ID será gerado pelo banco/backend
                nome = currentState.nomeCompleto,
                cpf = currentState.cpf,
                telefone = currentState.celular,
                email = currentState.email,
                dataNascimento = Instant.fromEpochMilliseconds(currentState.dataNascimentoMillis)
                    .toLocalDateTime(TimeZone.UTC).date,
                estadoCivil = currentState.estadoCivil,
                perfilFinanceiro = perfilFinanceiro,
                dadosInteresse = dadosInteresse,
                participante = currentState.participante,
                status = StatusCliente.PENDENTE,
                documentos = map
            )




            when (val resposta = clienteRepository.salvarCliente(cliente)) {
                is Resultado.Sucesso -> {
                    _uiState.update { it.copy(isSaving = false, saveSuccessClienteId = resposta.data) }
                    _formEvent.emit(ClienteFormEvent.ClienteSalvoComSucesso(resposta.data))
                }
                is Resultado.Erro -> {
                    _uiState.update { it.copy(isSaving = false, saveError = resposta.error.mensagem) }
                    _formEvent.emit(ClienteFormEvent.ErroAoSalvar(resposta.error.mensagem))
                }
            }
        }
    }

    @OptIn(ExperimentalEncodingApi::class)
    fun atualizarCliente() {
        viewModelScope.launch {
            _uiState.update { it.copy(isSaving = true, saveError = null, saveSuccessClienteId = null) }
            val currentState = _uiState.value // Pega o estado atual para construir o objeto Cliente

            val dadosInteresse = DadosInteresse(
                tipoImovel = currentState.tipoImovel,
                estadoImovel = currentState.estadoImovel
            )

            val perfilFinanceiro = PerfilFinanceiro(
                rendaBruta = currentState.rendaBruta.toDouble(),
                tipoRenda = currentState.tipoRenda,
                possuiRestricao = currentState.possuiRestricao,
                possuiDependente = currentState.possuiDependente,
                tresAnosFgts = currentState.tresAnosFgts,
                usarFgts = currentState.usarFgts
            )

            val map = mapOf<TipoDocumento, String>(
                TipoDocumento.RG to Base64.encode(currentState.docRg?.readBytes() ?: ByteArray(0)),
                TipoDocumento.CPF to Base64.encode(currentState.docCpf?.readBytes() ?: ByteArray(0)),
                TipoDocumento.COMPROVANTE_RESIDENCIA to Base64.encode(currentState.comprovanteResidencia?.readBytes() ?: ByteArray(0)),
                TipoDocumento.PIS_CARTAO_CIDADAO to Base64.encode(currentState.pisCartaoCidadao?.readBytes() ?: ByteArray(0)),
                TipoDocumento.CERTIDAO to Base64.encode(currentState.certidao?.readBytes() ?: ByteArray(0)),
                TipoDocumento.HOLERITE to Base64.encode(currentState.holerite?.readBytes() ?: ByteArray(0)),
                TipoDocumento.EXTRATO_FGTS to Base64.encode(currentState.extratoFgts?.readBytes() ?: ByteArray(0)),
                TipoDocumento.DECLARACAO_IR to Base64.encode(currentState.declaracaoIr?.readBytes() ?: ByteArray(0)),
                TipoDocumento.CARTEIRA_TRABALHO to Base64.encode(currentState.carteiraTrabalho?.readBytes() ?: ByteArray(0))
            )

            val cliente = Cliente(
                id = currentState.id, // ID será gerado pelo banco/backend
                nome = currentState.nomeCompleto,
                cpf = currentState.cpf,
                telefone = currentState.celular,
                email = currentState.email,
                dataNascimento = Instant.fromEpochMilliseconds(currentState.dataNascimentoMillis)
                    .toLocalDateTime(TimeZone.currentSystemDefault()).date, // Use currentSystemDefault ou UTC
                estadoCivil = currentState.estadoCivil,
                perfilFinanceiro = perfilFinanceiro,
                dadosInteresse = dadosInteresse,
                participante = currentState.participante,
                status = StatusCliente.PENDENTE,
                documentos = map,
            )




            when (val resposta = clienteRepository.atualizarCliente(cliente)) {
                is Resultado.Sucesso -> {
                    _uiState.update { it.copy(isSaving = false, saveSuccessClienteId = resposta.data) }
                    _formEvent.emit(ClienteFormEvent.ClienteSalvoComSucesso(resposta.data))
                }
                is Resultado.Erro -> {
                    _uiState.update { it.copy(isSaving = false, saveError = resposta.error.mensagem) }
                    _formEvent.emit(ClienteFormEvent.ErroAoSalvar(resposta.error.mensagem))
                }
            }
        }
    }

    @OptIn(ExperimentalEncodingApi::class)
    fun buscarClientePorCpf() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoadingCliente = true, clienteNaoEncontradoError = null, saveError = null, saveSuccessClienteId = null) }
            val currentState = _uiState.value
            val cpf = currentState.cpf
            when (val resultadoBusca = clienteRepository.buscarPorCpf(cpf)) {
                is Resultado.Sucesso -> {
                    val clienteEncontrado = resultadoBusca.data
                    _uiState.update {
                        it.copy(
                            id = clienteEncontrado.id,
                            nomeCompleto = clienteEncontrado.nome,
                            cpf = clienteEncontrado.cpf,
                            celular = clienteEncontrado.telefone,
                            email = clienteEncontrado.email,
                            dataNascimentoMillis = clienteEncontrado.dataNascimento.atStartOfDayIn(
                                TimeZone.UTC).toEpochMilliseconds(),
                            estadoCivil = clienteEncontrado.estadoCivil,
                            rendaBruta = clienteEncontrado.perfilFinanceiro.rendaBruta.toString(), // Formatar para exibição
                            tipoRenda = clienteEncontrado.perfilFinanceiro.tipoRenda,
                            possuiRestricao = clienteEncontrado.perfilFinanceiro.possuiRestricao,
                            possuiDependente = clienteEncontrado.perfilFinanceiro.possuiDependente,
                            tresAnosFgts = clienteEncontrado.perfilFinanceiro.tresAnosFgts,
                            usarFgts = clienteEncontrado.perfilFinanceiro.usarFgts,
                            tipoImovel = clienteEncontrado.dadosInteresse.tipoImovel,
                            estadoImovel = clienteEncontrado.dadosInteresse.estadoImovel,
                            participante = clienteEncontrado.participante.toString(),
                            pathRg = clienteEncontrado.documentos[TipoDocumento.RG],
                            pathCpf = clienteEncontrado.documentos[TipoDocumento.CPF],
                            pathComprovanteResidencia = clienteEncontrado.documentos[TipoDocumento.COMPROVANTE_RESIDENCIA],
                            pathPisCartaoCidadao = clienteEncontrado.documentos[TipoDocumento.PIS_CARTAO_CIDADAO],
                            pathCertidao = clienteEncontrado.documentos[TipoDocumento.CERTIDAO],
                            pathHolerite = clienteEncontrado.documentos[TipoDocumento.HOLERITE],
                            pathExtratoFgts = clienteEncontrado.documentos[TipoDocumento.EXTRATO_FGTS],
                            pathDeclaracaoIr = clienteEncontrado.documentos[TipoDocumento.DECLARACAO_IR],
                            pathCarteiraTrabalho = clienteEncontrado.documentos[TipoDocumento.CARTEIRA_TRABALHO],
                            isLoadingCliente = false
                        )
                    }
                    _formEvent.emit(ClienteFormEvent.ClienteCarregadoComSucesso(uiState.value.fromTela.toString()))
                }
                is Resultado.Erro -> {
                    _uiState.update {
                        it.copy(
                            isLoadingCliente = false,
                            clienteNaoEncontradoError = "Cliente com CPF $cpf não encontrado."
                        )
                    }
                    _formEvent.emit(ClienteFormEvent.ErroAoCarregarCliente("Cliente com CPF $cpf não encontrado."))
                }
            }
        }
    }


    fun deletarCliente() {
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    isSaving = true,
                    clienteNaoEncontradoError = null,
                    saveError = null,
                    saveSuccessClienteId = null
                )
            }
            val currentState = _uiState.value
            val id = currentState.id
            when (val resultado = clienteRepository.deletarCliente(id ?: 0)) {
                is Resultado.Sucesso -> {
                    _uiState.update { it.copy(isSaving = false) }
                    _formEvent.emit(ClienteFormEvent.DeletadoComSucesso)
                }

                is Resultado.Erro -> {
                    _uiState.update { it.copy(isSaving = false, saveError = resultado.error.mensagem) }
                    _formEvent.emit(ClienteFormEvent.ErroAoSalvar(resultado.error.mensagem))
                }

            }
        }
    }

    companion object {
        fun provideFactory(
            clienteRepository: ClienteRepository
        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: KClass<T>, extras: CreationExtras): T {
                return when (modelClass) {
                    ClienteViewModel::class -> ClienteViewModel(clienteRepository) as T
                    else -> throw IllegalArgumentException("ViewModel desconhecido")
                }
            }
        }
    }

}
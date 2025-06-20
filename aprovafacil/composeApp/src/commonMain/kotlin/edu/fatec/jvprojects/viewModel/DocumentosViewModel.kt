package edu.fatec.jvprojects.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import edu.fatec.jvprojects.model.Documentos
import edu.fatec.jvprojects.repository.DocumentosRepository
import edu.fatec.jvprojects.wrapper.Resultado
import io.github.vinceglb.filekit.PlatformFile
import io.github.vinceglb.filekit.readBytes
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.reflect.KClass

data class DocumentosUiState(
    val idCliente: Long = 0,
    val rg: PlatformFile? = null,
    val cpf: PlatformFile? = null,
    val comprovanteResidencia: PlatformFile? = null,
    val pisCartaoCidadao: PlatformFile? = null,
    val certidao: PlatformFile? = null,
    val holerite: PlatformFile? = null,
    val extratoFgts: PlatformFile? = null,
    val declaracaoIr: PlatformFile? = null,
    val carteiraTrabalho: PlatformFile? = null,
    val isSaving: Boolean = false,
    val saveError: String? = null,
)

sealed interface DocumentosFormEvent {
    data class DocumentosEnviadosComSucesso(val mensagem: String) : DocumentosFormEvent
    data class ErroAoEnviarDocumentos(val mensagem: String) : DocumentosFormEvent
}


class DocumentosViewModel(
    private val documentosRepository: DocumentosRepository = DocumentosRepository()

) : ViewModel() {
    private val _uiState = MutableStateFlow(DocumentosUiState())
    val uiState: StateFlow<DocumentosUiState> = _uiState

    private val _formEvent = MutableSharedFlow<DocumentosFormEvent>()
    val formEvent = _formEvent.asSharedFlow()

    fun onIdClienteChange(novoId: Long) {
        _uiState.update { it.copy(idCliente = novoId, isSaving = false, saveError = null) }
    }

    fun onRgChange(novoRg: PlatformFile?) {
        _uiState.update { it.copy(rg = novoRg, isSaving = false, saveError = null) }
    }

    fun onCpfChange(novoCpf: PlatformFile?) {
        _uiState.update { it.copy(cpf = novoCpf, isSaving = false, saveError = null) }
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

//    fun enviarDocumentos() {
//        viewModelScope.launch {
//            _uiState.update { it.copy(isSaving = true, saveError = null) }
//            val currentState = _uiState.value
//
//            val documentos = Documentos(
//                currentState.idCliente,
//                currentState.rg?.readBytes() ?: ByteArray(0),
//                currentState.cpf?.readBytes() ?: ByteArray(0),
//                currentState.comprovanteResidencia?.readBytes() ?: ByteArray(0),
//                currentState.pisCartaoCidadao?.readBytes() ?: ByteArray(0),
//                currentState.certidao?.readBytes() ?: ByteArray(0),
//                currentState.holerite?.readBytes() ?: ByteArray(0),
//                currentState.extratoFgts?.readBytes() ?: ByteArray(0),
//                currentState.declaracaoIr?.readBytes() ?: ByteArray(0),
//                currentState.carteiraTrabalho?.readBytes() ?: ByteArray(0)
//            )
//
//            when (val resposta = documentosRepository.enviarDocumentos(documentos)) {
//                is Resultado.Sucesso -> {
//                    _uiState.update { it.copy(isSaving = false) }
//                    _formEvent.emit(DocumentosFormEvent.DocumentosEnviadosComSucesso(resposta.data))
//                }
//
//                is Resultado.Erro -> {
//                    _uiState.update { it.copy(isSaving = false, saveError = resposta.error.error) }
//                    _formEvent.emit(DocumentosFormEvent.ErroAoEnviarDocumentos(resposta.error.error))
//                }
//            }
//        }
//    }

    companion object {
        fun provideFactory(
            documentosRepository: DocumentosRepository
        ) : ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: KClass<T>, extras: CreationExtras): T {
                return when (modelClass) {
                    DocumentosViewModel::class -> DocumentosViewModel(documentosRepository) as T
                    else -> throw IllegalArgumentException("ViewModel desconhecido")
                }
            }
        }
    }
}
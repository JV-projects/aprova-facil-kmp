package edu.fatec.jvprojects.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import edu.fatec.jvprojects.composables.TextWithLabel
import edu.fatec.jvprojects.compositionLocal.LocalTokenService
import edu.fatec.jvprojects.model.Atendimento
import edu.fatec.jvprojects.model.enums.StatusCliente
import edu.fatec.jvprojects.model.enums.TipoDocumento
import edu.fatec.jvprojects.repository.AdministradorRepository
import edu.fatec.jvprojects.repository.ClienteRepository
import edu.fatec.jvprojects.rotas.Tela
import edu.fatec.jvprojects.utils.extensions.convertMilisToDate
import edu.fatec.jvprojects.viewModel.ClienteViewModel
import edu.fatec.jvprojects.wrapper.Resultado
import kotlinx.coroutines.launch

@Composable
fun ClienteScreen(
    navController: NavController,
    snackbarState: SnackbarHostState,
    clienteViewModel: ClienteViewModel,
    administradorRepository: AdministradorRepository = remember { AdministradorRepository() }
) {
    val tokenService = LocalTokenService.current
    val coScope = rememberCoroutineScope()
    val uiState = clienteViewModel.uiState.collectAsState()
    var analise by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }

    Surface {
        Column(
            modifier = Modifier.fillMaxSize()
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Column(
                modifier = Modifier.fillMaxHeight()
                    .fillMaxWidth(0.8f),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text("Dados pessoais:", fontWeight = FontWeight.Bold, fontSize = 14.sp)
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {

                    TextWithLabel(label = "Nome", informacao = uiState.value.nomeCompleto)
                    TextWithLabel(label = "CPF", informacao = uiState.value.cpf)
                    TextWithLabel(label = "Celular", informacao = uiState.value.celular)
                    TextWithLabel(label = "Email", informacao = uiState.value.email)
                    TextWithLabel(
                        label = "Estado civil",
                        informacao = uiState.value.estadoCivil.name
                    )
                    TextWithLabel(
                        label = "Data de Nascimento",
                        informacao = uiState.value.dataNascimentoMillis.convertMilisToDate()
                    )
                }

                Text("Perfil financeiro:", fontWeight = FontWeight.Bold, fontSize = 14.sp)
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    TextWithLabel(
                        label = "Tipo de renda",
                        informacao = uiState.value.tipoRenda.name
                    )
                    TextWithLabel(
                        label = "Renda Bruta",
                        informacao = uiState.value.rendaBruta.toString()
                    )
                    TextWithLabel(
                        label = "Possui restrição?",
                        informacao = uiState.value.possuiRestricao.toString()
                    )
                    TextWithLabel(
                        label = "Possui dependente?",
                        informacao = uiState.value.possuiDependente.toString()
                    )
                    TextWithLabel(
                        label = "Usar FGTS?",
                        informacao = uiState.value.usarFgts.toString()
                    )
                    TextWithLabel(
                        label = "Três anos de FGTS?",
                        informacao = uiState.value.tresAnosFgts.toString()
                    )
                }

                Text("Interesse em:", fontWeight = FontWeight.Bold, fontSize = 14.sp)
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    TextWithLabel(
                        label = "Tipo imóvel",
                        informacao = uiState.value.tipoImovel.name
                    )
                    TextWithLabel(
                        label = "Estado do imóvel",
                        informacao = uiState.value.estadoImovel.name
                    )
                }

                Text("Documentos:", fontWeight = FontWeight.Bold, fontSize = 14.sp)
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    DocumentoListItem(TipoDocumento.RG) {
                        navController.navigate("documento/${uiState.value.pathRg?.replace("/", "%2F") ?: ""}")
                    }

                    DocumentoListItem(TipoDocumento.CPF) {
                        navController.navigate("documento/${uiState.value.pathCpf?.replace("/", "%2F") ?: ""}")
                    }

                    DocumentoListItem(TipoDocumento.CERTIDAO) {
                        navController.navigate("documento/${uiState.value.pathCertidao?.replace("/", "%2F") ?: ""}")

                    }

                    DocumentoListItem(TipoDocumento.COMPROVANTE_RESIDENCIA) {
                        navController.navigate("documento/${uiState.value.pathComprovanteResidencia?.replace("/", "%2F") ?: ""}")
                    }

                    DocumentoListItem(TipoDocumento.PIS_CARTAO_CIDADAO) {
                        navController.navigate("documento/${uiState.value.pathPisCartaoCidadao?.replace("/", "%2F") ?: ""}")
                    }

                    DocumentoListItem(TipoDocumento.HOLERITE) {
                        navController.navigate("documento/${uiState.value.pathHolerite?.replace("/", "%2F") ?: ""}")
                    }

                    DocumentoListItem(TipoDocumento.EXTRATO_FGTS) {
                        navController.navigate("documento/${uiState.value.pathExtratoFgts?.replace("/", "%2F") ?: ""}")
                    }

                    DocumentoListItem(TipoDocumento.DECLARACAO_IR) {
                        navController.navigate("documento/${uiState.value.pathDeclaracaoIr?.replace("/", "%2F") ?: ""}")
                    }

                    DocumentoListItem(TipoDocumento.CARTEIRA_TRABALHO) {
                        navController.navigate("documento/${uiState.value.pathCarteiraTrabalho?.replace("/", "%2F") ?: ""}")
                    }

                }

                TextWithLabel(
                    label = "CPF do participante",
                    informacao = uiState.value.participante
                )

                Text("Escreva a análise de crédito")
                OutlinedTextField(
                    modifier = Modifier.fillMaxSize(),
                    value = analise,
                    onValueChange = { analise = it },
                    label = { Text("Análise de crédito") },
                    minLines = 4
                )

                Text("Informe o email para qual corretor deseja destinar o atendimento desse cliente")
                Row {
                    OutlinedTextField(
                        modifier = Modifier.fillMaxSize(),
                        value = email,
                        onValueChange = { email = it },
                        label = { Text("Email") }
                    )
                    Text("@dominio.com")
                }

                Button(
                    enabled = analise.isNotBlank() && email.isNotBlank(),
                    onClick = {
                        coScope.launch {
                            val atendimento = Atendimento(
                                idCliente = uiState.value.id!!,
                                emailCorretor = "$email@fatec.sp.gov.br",
                                analiseCredito = analise
                            )
                            when (val resposta = administradorRepository.distribuirCliente(atendimento, tokenService.getToken())) {
                                is Resultado.Sucesso -> {
                                    navController.popBackStack()
                                }
                                is Resultado.Erro -> {
                                    snackbarState.showSnackbar(
                                        "Erro: ${resposta.error.mensagem}"
                                    )
                                }
                            }
                        }
                    }
                ) {
                    Text("Distribuir cliente")
                }
            }
        }
    }
}

@Composable
fun DocumentoListItem(
    tipoDoc: TipoDocumento,
    onClick: () -> Unit
) {
    ListItem(
        modifier = Modifier.clickable(onClick = onClick),
        headlineContent = { Text(tipoDoc.name) },
    )
}
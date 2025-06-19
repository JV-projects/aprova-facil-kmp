package edu.fatec.jvprojects.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
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
import androidx.navigation.NavController
import edu.fatec.jvprojects.composables.TextWithLabel
import edu.fatec.jvprojects.model.Cliente
import edu.fatec.jvprojects.repository.ClienteRepository
import edu.fatec.jvprojects.rotas.Tela
import edu.fatec.jvprojects.utils.extensions.boolToVal
import edu.fatec.jvprojects.utils.extensions.convertMilisToDate
import edu.fatec.jvprojects.viewModel.ClienteViewModel
import edu.fatec.jvprojects.wrapper.Resultado
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json

@Composable
fun DetalheScreen(
    navController: NavController,
    snackbarHost: SnackbarHostState,
    clienteViewModel: ClienteViewModel,
) {

    val uiState = clienteViewModel.uiState.collectAsState()

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
                    TextWithLabel(label = "Estado civil", informacao = uiState.value.estadoCivil.name)
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
                        informacao = uiState.value.possuiRestricao.boolToVal()
                    )
                    TextWithLabel(
                        label = "Possui dependente?",
                        informacao = uiState.value.possuiDependente.boolToVal()
                    )
                    TextWithLabel(
                        label = "Usar FGTS?",
                        informacao = uiState.value.usarFgts.boolToVal()
                    )
                    TextWithLabel(
                        label = "Três anos de FGTS?",
                        informacao = uiState.value.tresAnosFgts.boolToVal()
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
                    TextWithLabel(
                        label = "RG",
                        informacao = uiState.value.pathRg ?: ""
                    )
                    TextWithLabel(
                        label = "CPF",
                        informacao = uiState.value.pathCpf ?: ""
                    )
                    TextWithLabel(
                        label = "Comprovante de residência",
                        informacao = uiState.value.pathComprovanteResidencia ?: ""
                    )
                    TextWithLabel(
                        label = "PIS/Cartão de Cidadão",
                        informacao = uiState.value.pathPisCartaoCidadao ?: ""
                    )
                    TextWithLabel(
                        label = "Certidão",
                        informacao = uiState.value.pathCertidao ?: ""
                    )
                    TextWithLabel(
                        label = "Holerite",
                        informacao = uiState.value.pathHolerite ?: ""
                    )
                    TextWithLabel(
                        label = "Extrato FGTS",
                        informacao = uiState.value.pathExtratoFgts ?: ""
                    )
                    TextWithLabel(
                        label = "Declaração de IR",
                        informacao = uiState.value.pathDeclaracaoIr ?: ""
                    )
                    TextWithLabel(
                        label = "Carteira de Trabalho",
                        informacao = uiState.value.pathCarteiraTrabalho ?: ""
                    )

                }

                TextWithLabel(
                    label = "CPF do participante",
                    informacao = uiState.value.participante
                )

                if (uiState.value.fromTela == Tela.Excluir.rota) {
                    Button(
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.error,
                            contentColor = MaterialTheme.colorScheme.onError,
                        ),
                        onClick = {
                            clienteViewModel.deletarCliente()
                        }
                    ) {
                        Text("Deletar cadastro")
                    }
                }
            }
        }
    }
}
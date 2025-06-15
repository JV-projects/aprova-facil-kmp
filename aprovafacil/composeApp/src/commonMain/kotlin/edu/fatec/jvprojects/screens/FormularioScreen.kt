package edu.fatec.jvprojects.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import edu.fatec.jvprojects.composables.DatePicker
import edu.fatec.jvprojects.composables.Select
import edu.fatec.jvprojects.model.Cliente
import edu.fatec.jvprojects.model.DadosInteresse
import edu.fatec.jvprojects.model.PerfilFinanceiro
import edu.fatec.jvprojects.model.enums.EstadoCivil
import edu.fatec.jvprojects.model.enums.EstadoImovel
import edu.fatec.jvprojects.model.enums.TipoImovel
import edu.fatec.jvprojects.model.enums.TipoRenda
import edu.fatec.jvprojects.repository.ClienteRepository
import edu.fatec.jvprojects.viewModel.ClienteFormEvent
import edu.fatec.jvprojects.viewModel.ClienteViewModel
import edu.fatec.jvprojects.wrapper.Resultado
import kotlinx.coroutines.launch
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlin.math.exp

@Composable
fun FormularioScreen(
    navController: NavController,
    snackbarState: SnackbarHostState,
    clienteRepository: ClienteRepository = remember { ClienteRepository() },
    clienteViewModel: ClienteViewModel
) {
    val uiState = clienteViewModel.uiState.collectAsState()

    println(clienteViewModel)
    println(uiState)

    var modalOpen by remember { mutableStateOf(false) }
    var options = remember { mutableStateOf(EstadoCivil.entries.map { it.name }) }
    var expanded = remember { mutableStateOf(false) }

    Surface {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.TopCenter
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(0.8F)
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {


                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text("Nome completo") },
                    value = uiState.value.nomeCompleto,
                    onValueChange = { clienteViewModel.onNomeCompletoChange(it)}
                )

                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text("CPF") },
                    value = uiState.value.cpf,
                    onValueChange = { clienteViewModel.onCpfChange(it) }
                )

                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text("Celular") },
                    value = uiState.value.celular,
                    onValueChange = { clienteViewModel.onCelularChange(it) }
                )

                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text("E-mail") },
                    value = uiState.value.email,
                    onValueChange = { clienteViewModel.onEmailChange(it) }
                )

                DatePicker(
                    modifier = Modifier.align(Alignment.Start),
                    onDateSelected = {
                        if (it != null) {
                            clienteViewModel.onDataNascimentoChange(it)
                        }
                    },
                    onDismiss = { modalOpen = !modalOpen },
                    dataSelecionada = uiState.value.dataNascimentoMillis,
                    modalOpen = modalOpen,
                    label = "Data de nascimento:"
                )

                Column(modifier = Modifier.align(Alignment.Start)) {
                    Select(
                        options.value,
                        expanded.value,
                        { expanded.value = it },
                        uiState.value.estadoCivil.toString(),
                        { clienteViewModel.onEstadoCivilChange(EstadoCivil.valueOf(it)) }
                    )
                }

                Column(
                    modifier = Modifier.padding(5.dp)
                        .align(Alignment.Start)
                ) {
                    Text("Tipo de renda:", textAlign = TextAlign.Center)
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        RadioButton(
                            selected = uiState.value.tipoRenda == TipoRenda.FORMAL,
                            onClick = { clienteViewModel.onTipoRendaChange(TipoRenda.FORMAL) }
                        )
                        Text("Formal")

                        RadioButton(
                            selected = uiState.value.tipoRenda == TipoRenda.AUTONOMO,
                            onClick = { clienteViewModel.onTipoRendaChange(TipoRenda.AUTONOMO) }
                        )
                        Text("Autônomo")
                    }
                }

                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text("Renda bruta") },
                    value = uiState.value.rendaBruta,
                    onValueChange = { clienteViewModel.onRendaBrutaChange(it) }
                )


                Column(
                    modifier = Modifier.padding(5.dp)
                        .align(Alignment.Start),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text("Restrição no nome?", textAlign = TextAlign.Center)
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        RadioButton(
                            selected = uiState.value.possuiRestricao,
                            onClick = { clienteViewModel.onPossuiRestricaoChange(true) }
                        )
                        Text("Sim")

                        RadioButton(
                            selected = !uiState.value.possuiRestricao,
                            onClick = { clienteViewModel.onPossuiRestricaoChange(false) }
                        )
                        Text("Não")
                    }
                }

                Column(
                    modifier = Modifier.padding(5.dp)
                        .align(Alignment.Start),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text("Possui dependentes?", textAlign = TextAlign.Center)
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        RadioButton(
                            selected = uiState.value.possuiDependente,
                            onClick = { clienteViewModel.onPossuiDependentesChange(true) }
                        )
                        Text("Sim")

                        RadioButton(
                            selected = !uiState.value.possuiDependente,
                            onClick = { clienteViewModel.onPossuiDependentesChange(false) }
                        )
                        Text("Não")
                    }
                }

                Column(
                    modifier = Modifier.padding(5.dp)
                        .align(Alignment.Start),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text("Pretende utilizar FGTS?", textAlign = TextAlign.Center)
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        RadioButton(
                            selected = uiState.value.usarFgts,
                            onClick = { clienteViewModel.onUsarFgtsChange(true) }
                        )
                        Text("Sim")

                        RadioButton(
                            selected = !uiState.value.usarFgts,
                            onClick = { clienteViewModel.onUsarFgtsChange(false) }
                        )
                        Text("Não")
                    }
                }

                Column(
                    modifier = Modifier.padding(5.dp)
                        .align(Alignment.Start),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text("Possui 3 anos de FGTS?", textAlign = TextAlign.Center)
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        RadioButton(
                            selected = uiState.value.tresAnosFgts,
                            onClick = { clienteViewModel.onTresAnosFgtsChange(true) }
                        )
                        Text("Sim")

                        RadioButton(
                            selected = !uiState.value.tresAnosFgts,
                            onClick = { clienteViewModel.onTresAnosFgtsChange(false) }
                        )
                        Text("Não")
                    }
                }

                Column(
                    modifier = Modifier.padding(5.dp)
                        .align(Alignment.Start),
                    horizontalAlignment = Alignment.Start
                ) {
                    Text("Procura que tipo de imóvel?", textAlign = TextAlign.Center)
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        RadioButton(
                            selected = uiState.value.tipoImovel == TipoImovel.CASA,
                            onClick = { clienteViewModel.onTipoImovelChange(TipoImovel.CASA) }
                        )
                        Text("Casa")

                        RadioButton(
                            selected = uiState.value.tipoImovel == TipoImovel.APARTAMENTO,
                            onClick = { clienteViewModel.onTipoImovelChange(TipoImovel.APARTAMENTO) }
                        )
                        Text("Apartamento")
                    }
                }

                Column(
                    modifier = Modifier.padding(5.dp)
                        .align(Alignment.Start),
                    horizontalAlignment = Alignment.Start
                ) {
                    Text("Estado do imóvel?", textAlign = TextAlign.Center)
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        RadioButton(
                            selected = uiState.value.estadoImovel == EstadoImovel.NOVO,
                            onClick = {  clienteViewModel.onEstadoImovelChange(EstadoImovel.NOVO) }
                        )
                        Text("Novo")

                        RadioButton(
                            selected = uiState.value.estadoImovel == EstadoImovel.USADO,
                            onClick = { clienteViewModel.onEstadoImovelChange(EstadoImovel.USADO) }
                        )
                        Text("Velho")
                    }
                }

                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text("Informe o CPF do participante para somar renda") },
                    value = uiState.value.participante,
                    onValueChange = { clienteViewModel.onParticipanteChange(it) }
                )


            }
        }
    }
}
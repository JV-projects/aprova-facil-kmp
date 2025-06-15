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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
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
import edu.fatec.jvprojects.wrapper.Resultado
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Formulario(navController: NavController, snackbarState: SnackbarHostState) {
    val repository = ClienteRepository()
    val coScope = rememberCoroutineScope()

    var id by remember { mutableStateOf(0L) }
    var nomecompleto by remember { mutableStateOf("") }
    var cpf by remember { mutableStateOf("") }
    var celular by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var dataNasc by remember { mutableLongStateOf(Clock.System.now().toEpochMilliseconds()) }
    var modalOpen by remember { mutableStateOf(false) }
    var options = remember { mutableStateOf(EstadoCivil.entries.map { it.name }) }
    var expanded by remember { mutableStateOf(false) }
    var estadoCivil = remember { mutableStateOf(EstadoCivil.SOLTEIRO) }
    var rendaBruta by remember { mutableStateOf("") }
    var tipoRenda by remember { mutableStateOf(TipoRenda.FORMAL) }
    var possuiRestricao by remember { mutableStateOf(false) }
    var possuiDependentes by remember { mutableStateOf(false) }
    var tresAnosFgts by remember { mutableStateOf(false) }
    var usarFgts by remember { mutableStateOf(false) }
    var tipoImovel by remember { mutableStateOf(TipoImovel.CASA) }
    var estadoImovel by remember { mutableStateOf(EstadoImovel.NOVO) }

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
                Button(
                    onClick = { navController.navigate("consulta") }
                ) {
                    Text("Consultar cadastro")
                }

                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text("Nome completo") },
                    value = nomecompleto,
                    onValueChange = { nomecompleto = it }
                )

                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text("CPF") },
                    value = cpf,
                    onValueChange = { cpf = it }
                )

                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text("Celular") },
                    value = celular,
                    onValueChange = { celular = it }
                )

                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text("E-mail") },
                    value = email,
                    onValueChange = { email = it }
                )

                DatePicker(
                    modifier = Modifier.align(Alignment.Start),
                    onDateSelected = {
                        if (it != null) {
                            dataNasc = it
                        }
                    },
                    onDismiss = { modalOpen = !modalOpen },
                    dataSelecionada = dataNasc,
                    modalOpen = modalOpen,
                    label = "Data de nascimento:"
                )
                
                Column(modifier = Modifier.align(Alignment.Start)) {
                    Select(
                        options.value,
                        expanded,
                        {  expanded = it },
                        estadoCivil.value.toString(),
                        { estadoCivil.value = EstadoCivil.valueOf(it) }
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
                            selected = tipoRenda == TipoRenda.FORMAL,
                            onClick = { tipoRenda = TipoRenda.FORMAL }
                        )
                        Text("Formal")

                        RadioButton(
                            selected = tipoRenda == TipoRenda.AUTONOMO,
                            onClick = { tipoRenda = TipoRenda.AUTONOMO }
                        )
                        Text("Autônomo")
                    }
                }

                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text("Renda bruta") },
                    value = rendaBruta,
                    onValueChange = { rendaBruta = it }
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
                            selected = possuiRestricao,
                            onClick = { possuiRestricao = true }
                        )
                        Text("Sim")

                        RadioButton(
                            selected = !possuiRestricao,
                            onClick = { possuiRestricao = false }
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
                            selected = possuiDependentes,
                            onClick = { possuiDependentes = true }
                        )
                        Text("Sim")

                        RadioButton(
                            selected = !possuiDependentes,
                            onClick = { possuiDependentes = false }
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
                            selected = usarFgts,
                            onClick = { usarFgts = true }
                        )
                        Text("Sim")

                        RadioButton(
                            selected = !usarFgts,
                            onClick = { usarFgts = false }
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
                            selected = tresAnosFgts,
                            onClick = { tresAnosFgts = true }
                        )
                        Text("Sim")

                        RadioButton(
                            selected = !tresAnosFgts,
                            onClick = { tresAnosFgts = false }
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
                            selected = tipoImovel == TipoImovel.CASA,
                            onClick = { tipoImovel = TipoImovel.CASA }
                        )
                        Text("Casa")

                        RadioButton(
                            selected = tipoImovel == TipoImovel.APARTAMENTO,
                            onClick = { tipoImovel = TipoImovel.APARTAMENTO }
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
                            selected = estadoImovel == EstadoImovel.NOVO,
                            onClick = { estadoImovel = EstadoImovel.NOVO }
                        )
                        Text("Novo")

                        RadioButton(
                            selected = estadoImovel == EstadoImovel.USADO,
                            onClick = { estadoImovel = EstadoImovel.USADO }
                        )
                        Text("Velho")
                    }
                }

                Button(
                    onClick = {
                        val dadosInteresse = DadosInteresse(
                            tipoImovel,
                            estadoImovel
                        )

                        val perfilFinanceiro = PerfilFinanceiro(
                            rendaBruta.toDouble(),
                            tipoRenda,
                            possuiRestricao,
                            possuiDependentes,
                            tresAnosFgts,
                            usarFgts
                        )

                        val cliente = Cliente(
                            id = null,
                            nome = nomecompleto,
                            cpf = cpf,
                            telefone = celular,
                            email = email,
                            statusCadastro = "PENDENTE",
                            dataNascimento = Instant.
                            fromEpochMilliseconds(dataNasc).toLocalDateTime(TimeZone.UTC).date,
                            estadoCivil = estadoCivil.value,
                            perfilFinanceiro = perfilFinanceiro,
                            dadosInteresse = dadosInteresse,
                            documentos = mapOf(),
                            participante = "",
                        )

                        coScope.launch {
                            when (val resposta = repository.salvarCliente(cliente)) {
                                is Resultado.Sucesso -> {
                                    id = resposta.data
                                    snackbarState.showSnackbar(
                                        message = "Cliente com id: " + resposta.data.toString() +
                                        " salvo com sucesso"
                                    )
                                }
                                is Resultado.Erro -> {
                                    snackbarState.showSnackbar(
                                        message = resposta.error.mensagem
                                    )
                                }
                            }
                        }
                    }
                ) {
                    Text("Salvar e enviar")
                }

            }
        }
    }
}
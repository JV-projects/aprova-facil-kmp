package edu.fatec.jvprojects.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import edu.fatec.jvprojects.composables.DatePicker
import edu.fatec.jvprojects.model.Cliente
import edu.fatec.jvprojects.model.DadosInteresse
import edu.fatec.jvprojects.model.PerfilFinanceiro
import edu.fatec.jvprojects.model.enums.TipoImovel
import edu.fatec.jvprojects.model.enums.TipoRenda
import edu.fatec.jvprojects.repository.ClienteRepository
import edu.fatec.jvprojects.wrapper.ResultadoBusca
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.atStartOfDayIn
import kotlinx.datetime.toLocalDateTime
import kotlinx.serialization.json.Json

@Composable
fun EditarScreen(navController: NavController) {
    val repository = ClienteRepository()
    val coScope = rememberCoroutineScope()
    val oldCliente = navController.
    previousBackStackEntry?.
    savedStateHandle?.
    get<String>("cliente") ?: ""

    val cliente = Json.decodeFromString<Cliente>(oldCliente)

    var nomecompleto by remember { mutableStateOf(cliente.nome) }
    var cpf by remember { mutableStateOf(cliente.cpf) }
    var celular by remember { mutableStateOf(cliente.telefone) }
    var email by remember { mutableStateOf(cliente.email) }
    var dataNasc by remember { mutableLongStateOf(cliente.dataNascimento.atStartOfDayIn(TimeZone.UTC).toEpochMilliseconds()) }
    var modalOpen by remember { mutableStateOf(false) }
    var tipoRenda by remember { mutableStateOf(cliente.perfilFinanceiro.tipoRenda) }
    var rendaBruta by remember { mutableStateOf(cliente.perfilFinanceiro.rendaBruta.toString()) }
    var restricaoNome by remember { mutableStateOf(cliente.perfilFinanceiro.possuiRestricao) }
    var dependentes by remember { mutableStateOf(false) }
    var interesse by remember { mutableStateOf(cliente.dadosInteresse.tipoImovel) }
    var interesseRegiao by remember { mutableStateOf("") }

    Box(
        modifier = Modifier.fillMaxSize().background(color = Color.White),
        contentAlignment = Alignment.TopCenter
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(0.8F)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Button(
                onClick = { navController.popBackStack() }
            ) {
                Text("Voltar")
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
                        selected = tipoRenda == TipoRenda.INFORMAL,
                        onClick = { tipoRenda = TipoRenda.INFORMAL }
                    )
                    Text("Informal")
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
                        selected = restricaoNome,
                        onClick = { restricaoNome = true }
                    )
                    Text("Sim")

                    RadioButton(
                        selected = !restricaoNome,
                        onClick = { restricaoNome = false }
                    )
                    Text("Não")
                }
            }

            Column(
                modifier = Modifier.padding(5.dp)
                    .align(Alignment.Start),
                horizontalAlignment = Alignment.Start
            ) {
                Text("Procura que tipo de ímovel?", textAlign = TextAlign.Center)
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    RadioButton(
                        selected = interesse == TipoImovel.CASA,
                        onClick = { interesse = TipoImovel.CASA }
                    )
                    Text("Casa")

                    RadioButton(
                        selected = interesse == TipoImovel.APARTAMENTO,
                        onClick = { interesse = TipoImovel.APARTAMENTO }
                    )
                    Text("Apartamento")
                }
            }

            Button(
                onClick = {
                    val dadosInteresse = DadosInteresse(
                        tipoImovel = interesse,
                    )

                    val perfilFinanceiro = PerfilFinanceiro(
                        rendaBruta.toDouble(),
                        tipoRenda,
                        restricaoNome,
                        false
                    )

                    val newCliente = Cliente(
                        id = cliente.id,
                        nome = nomecompleto,
                        cpf = cpf,
                        telefone = celular,
                        email = email,
                        status = "PENDENTE",
                        dataNascimento = Instant.fromEpochMilliseconds(dataNasc).toLocalDateTime(TimeZone.UTC).date,
                        perfilFinanceiro = perfilFinanceiro,
                        dadosInteresse = dadosInteresse
                    )

                    coScope.launch {
                        repository.atualizarCliente(newCliente)

                        navController.navigate("consulta")
                    }
                }
            ) {
                Text("Salvar e atualizar")
            }

        }
    }
}
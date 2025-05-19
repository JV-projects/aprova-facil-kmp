package edu.fatec.jvprojects.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import edu.fatec.jvprojects.composables.TextWithLabel
import edu.fatec.jvprojects.model.Cliente
import edu.fatec.jvprojects.repository.ClienteRepository
import edu.fatec.jvprojects.wrapper.ResultadoBusca
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json

@Composable
fun DetalheScreen(navController: NavController) {
    var clienteBusca by remember { mutableStateOf<Cliente?>(null) }
    var erro by remember { mutableStateOf<String?>(null) }
    val coScope = rememberCoroutineScope()
    val repository = ClienteRepository()

    val cpfCliente = navController
        .previousBackStackEntry
        ?.savedStateHandle
        ?.get<String>("cpf") ?: ""

    coScope.launch {
        val resultadoBusca = repository.buscarPorCpf(cpfCliente)

        if (resultadoBusca is Cliente) {
            clienteBusca = resultadoBusca
        } else if (resultadoBusca is String) {
            erro = resultadoBusca
        }
    }

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
                Button(
                    onClick = { navController.popBackStack() },
                ) {
                    Text("Voltar")
                }

                Button(
                    onClick = {
                        val json = Json.encodeToString(clienteBusca)

                        println("json encode: $json")

                        navController.currentBackStackEntry
                            ?.savedStateHandle
                            ?.set("cliente", json)
                        navController.navigate("editar")
                    }
                ) {
                    Text("Editar cadastro")
                }

                Button(
                    modifier = Modifier.background(Color(0xD95656)),
                    onClick = {
                        coScope.launch {
                            clienteBusca?.let {
                                repository.deletarCliente(it.id)

                                navController.popBackStack()
                            }
                        }
                    }
                ) {
                    Text("Deletar cadastro")
                }



                clienteBusca?.let {
                    val cliente = it

                    Text("Dados pessoais:", fontWeight = FontWeight.Bold, fontSize = 14.sp)
                    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        TextWithLabel(label = "Nome", informacao = cliente.nome)
                        TextWithLabel(label = "CPF", informacao = cliente.cpf)
                        TextWithLabel(label = "Celular", informacao = cliente.telefone)
                        TextWithLabel(label = "Email", informacao = cliente.email)
                        TextWithLabel(
                            label = "Data de Nascimento",
                            informacao = cliente.dataNascimento.toString()
                        )
                    }

                    Text("Perfil financeiro:", fontWeight = FontWeight.Bold, fontSize = 14.sp)
                    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        TextWithLabel(
                            label = "Renda Bruta",
                            informacao = cliente.perfilFinanceiro.rendaBruta.toString()
                        )
                        TextWithLabel(
                            label = "Tipo de renda",
                            informacao = cliente.perfilFinanceiro.tipoRenda.toString()
                        )
                    }

                    Text("Interesse:", fontWeight = FontWeight.Bold, fontSize = 14.sp)
                    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        TextWithLabel(
                            label = "Procura",
                            informacao = cliente.dadosInteresse.tipoImovel.toString()
                        )
                    }
                }
            }
        }
    }
    }
package edu.fatec.jvprojects.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import edu.fatec.jvprojects.composables.TextWithLabel
import edu.fatec.jvprojects.repository.ClienteRepository
import edu.fatec.jvprojects.wrapper.ResultadoBusca

@Composable
fun DetalheScreen(navController: NavController) {
    var resultadoBusca by remember { mutableStateOf<ResultadoBusca>(ResultadoBusca.Carregando) }
    val repository = ClienteRepository()

    val cpfCliente = navController
        .previousBackStackEntry
        ?.savedStateHandle
        ?.get<String>("cpf") ?: ""

    LaunchedEffect(Unit) {
        resultadoBusca = repository.buscarPorCpf(cpfCliente)
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(
            modifier = Modifier.fillMaxHeight()
                .fillMaxWidth(0.8f),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Button(
                onClick = { navController.navigate("consulta") },
            ) {
                Text("Voltar")
            }
            when (resultadoBusca) {
                is ResultadoBusca.Carregando -> {
                    Text("Carregando...")
                }

                is ResultadoBusca.Erro -> {
                    Text((resultadoBusca as ResultadoBusca.Erro).mensagem, color = Color.Red)
                }

                is ResultadoBusca.Sucesso -> {
                    val cliente = (resultadoBusca as ResultadoBusca.Sucesso).cliente

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
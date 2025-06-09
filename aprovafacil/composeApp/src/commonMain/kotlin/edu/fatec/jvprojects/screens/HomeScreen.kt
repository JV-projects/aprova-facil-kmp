package edu.fatec.jvprojects.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import edu.fatec.jvprojects.rotas.Tela
import edu.fatec.jvprojects.theme.AppFontFamily
import edu.fatec.jvprojects.viewModel.ClienteViewModel

@Composable
fun HomeScreen(
    navController: NavController,
    clienteViewModel: ClienteViewModel,
) {

    println(clienteViewModel)

    Surface {
        Box(
            modifier = Modifier.fillMaxSize(),
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(0.8f)
                    .align(Alignment.Center),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(
                    style = MaterialTheme.typography.displaySmall,
                    textAlign = TextAlign.Center,
                    text = "Bem vindo ao Aprova Fácil"
                )
                Text(
                    fontSize = 22.sp,
                    textAlign = TextAlign.Center,
                    text = "Escolha uma das opções"
                )
                Button(
                    onClick = {
                        navController.navigate(Tela.Formulario.rota)
                    }
                ) {
                    Text("Solicitar análise de crédito")
                }
                Button(
                    onClick = {
                        clienteViewModel.onTelaChange(Tela.Detalhes.rota)
                        navController.navigate(Tela.Consulta.rota)
                    }
                ) {
                    Text("Consultar minhas informações")
                }
                Button(
                    onClick = {
                        clienteViewModel.onTelaChange(Tela.Editar.rota)
                        navController.navigate(Tela.Consulta.rota)
                    }
                ) {
                    Text("Editar minhas informações")
                }
                Button(
                    onClick = {
                        clienteViewModel.onTelaChange(Tela.Excluir.rota)
                        navController.navigate(Tela.Consulta.rota)
                    }
                ) {
                    Text("Excluir minhas informações")
                }

            }
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomEnd),
                contentAlignment = Alignment.BottomEnd
            ) {
                Button(
                    onClick = {
                        navController.navigate("login")
                    }
                ) {
                    Text("Acesso interno")
                }
            }
        }
    }
}

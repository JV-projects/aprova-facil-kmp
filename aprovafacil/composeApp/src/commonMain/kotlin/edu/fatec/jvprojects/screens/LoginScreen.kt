package edu.fatec.jvprojects.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import edu.fatec.jvprojects.compositionLocal.LocalTokenService
import edu.fatec.jvprojects.model.Credenciais
import edu.fatec.jvprojects.repository.AdministradorRepository
import edu.fatec.jvprojects.rotas.Tela
import edu.fatec.jvprojects.wrapper.Resultado
import kotlinx.coroutines.launch

@Composable
fun LoginScreen(
    administradorRepository: AdministradorRepository,
    navController: NavController,
    snackbarState: SnackbarHostState
) {

    val tokenService = LocalTokenService.current
    val coScope = rememberCoroutineScope()
    var email by remember { mutableStateOf("") }
    var senha by remember { mutableStateOf("") }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(0.8f)
                .align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = "Acesso administrativo",
                style = MaterialTheme.typography.headlineMedium
            )
            Text(
                text = "Insira suas credenciais de administrador"
            )
            OutlinedTextField(
                label = { Text("E-mail") },
                value = email,
                onValueChange = { email = it }
            )
            OutlinedTextField(
                label = { Text("Senha") },
                value = senha,
                onValueChange = { senha = it },
                visualTransformation = PasswordVisualTransformation()
            )
            Button(
                onClick = {
                    coScope.launch {
                        val credenciais = Credenciais(email, senha)
                        when (val resposta = administradorRepository.login(credenciais)) {
                            is Resultado.Sucesso -> {
                                val token = resposta.data.getValue("token")
                                tokenService.saveToken(token)
                                navController.navigate(Tela.Dashboard.rota)
                            }
                            is Resultado.Erro -> {
                                snackbarState.showSnackbar(
                                    message = "Erro: ${resposta.error.mensagem}"
                                )
                            }
                        }
                    }
                }
            ) {
                Text("Login")
            }
        }
    }
}
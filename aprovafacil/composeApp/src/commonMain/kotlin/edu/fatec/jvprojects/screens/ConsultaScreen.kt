package edu.fatec.jvprojects.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import edu.fatec.jvprojects.viewModel.ClienteViewModel

@Composable
fun ConsultaScreen(
    navController: NavController,
    snackbarState: SnackbarHostState,
    clienteViewModel: ClienteViewModel
) {
    val uiSate = clienteViewModel.uiState.collectAsState()

    println("ConsultaScreen: $clienteViewModel")

    Surface {
        Box(
            modifier = Modifier.fillMaxSize()
                .verticalScroll(rememberScrollState()),
            contentAlignment = Alignment.TopCenter
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(0.8f),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(
                    text = "Digite seu CPF para encontrar seus dados:"
                )
                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text("CPF") },
                    value = uiSate.value.cpf,
                    onValueChange = { clienteViewModel.onCpfChange(it) },
                )


            }
        }
    }
}
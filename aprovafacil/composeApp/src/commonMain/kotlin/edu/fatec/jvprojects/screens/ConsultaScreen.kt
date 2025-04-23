package edu.fatec.jvprojects.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun ConsultaScreen(navController: NavController) {
    var cpf by remember { mutableStateOf("") }

    Box(
        modifier = Modifier.fillMaxSize()
            .background(color = Color.White)
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
                value = cpf,
                onValueChange = { cpf = it }
            )
            Button(
                onClick = {
                    if (cpf.isNotBlank()) {
                        navController.currentBackStackEntry
                            ?.savedStateHandle
                            ?.set("cpf", cpf)
                        navController.navigate("detalhes")
                    }
                },
            ) {
                Text("Buscar")
            }

            Button(
                onClick = { navController.popBackStack() }
            ) {
                Text("Voltar")
            }
        }
    }
}
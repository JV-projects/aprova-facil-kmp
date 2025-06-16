package edu.fatec.jvprojects.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import edu.fatec.jvprojects.composables.StatusBadge
import edu.fatec.jvprojects.model.Cliente
import edu.fatec.jvprojects.model.enums.StatusCliente
import edu.fatec.jvprojects.repository.AdministradorRepository
import edu.fatec.jvprojects.viewModel.ClienteViewModel
import edu.fatec.jvprojects.wrapper.Resultado
import kotlinx.coroutines.launch

@Composable
fun DashboardScreen(
    clienteViewModel: ClienteViewModel,
    administradorRepository: AdministradorRepository,
    navController: NavController,
    snackbarState: SnackbarHostState
) {
    val coScope = rememberCoroutineScope()
    val enumList = StatusCliente.entries.toMutableList()
    val tabs = StatusCliente.entries.toMutableList().map { it.textoExibicao }
    val clientes = remember { mutableStateListOf<Cliente>() }
    var tabIndex by remember { mutableStateOf(0) }

    fun detalhesCliente(cpf: String) {
        clienteViewModel.onCpfChange(cpf)
        clienteViewModel.buscarClientePorCpf()
    }

    LaunchedEffect(Unit) {
        when (val resposta = administradorRepository.listarClientes()) {
            is Resultado.Sucesso -> {
                println(resposta.data)
                clientes.addAll(resposta.data)
            }
            is Resultado.Erro -> {
                snackbarState.showSnackbar(
                    "Erro: ${resposta.error.mensagem}"
                )
            }
        }
    }

    Box {
        Column {
            ScrollableTabRow(
                selectedTabIndex = tabIndex
            ) {
                tabs.forEachIndexed { index, status ->
                    Tab(
                        selected = tabIndex == index,
                        onClick = {
                            tabIndex = index
                            println(index)
                            println(status)
                            coScope.launch {
                                when (val resposta = administradorRepository.buscarPorStatus(enumList[index])) {
                                    is Resultado.Sucesso -> {
                                        clientes.clear()
                                        clientes.addAll(resposta.data)
                                    }
                                    is Resultado.Erro -> {
                                        snackbarState.showSnackbar(
                                            "Erro: ${resposta.error.mensagem}"
                                        )
                                    }
                                }
                            }
                        },
                        text = {
                            Text(status.toString())
                        }
                    )

                }
            }

            Text(
                text = "Gerenciamento de Clientes",
                style = MaterialTheme.typography.headlineMedium
            )
            clientes.forEach { cliente ->
                println(cliente.status)
                ClienteListItem(cliente.nome, cliente.status) { detalhesCliente(cliente.cpf) }
            }
        }
    }
}

@Composable
fun ClienteListItem(
    nome: String,
    status: StatusCliente,
    onClick: () -> Unit
) {
    ListItem(
        modifier = Modifier.clickable(onClick = onClick),
        headlineContent = { Text(nome) },
        trailingContent = { StatusBadge(status.name) },
    )
}
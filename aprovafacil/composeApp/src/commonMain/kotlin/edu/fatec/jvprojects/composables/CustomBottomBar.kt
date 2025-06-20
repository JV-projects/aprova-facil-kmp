package edu.fatec.jvprojects.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import edu.fatec.jvprojects.repository.ClienteRepository
import edu.fatec.jvprojects.repository.DocumentosRepository
import edu.fatec.jvprojects.rotas.Tela
import edu.fatec.jvprojects.viewModel.ClienteViewModel
import edu.fatec.jvprojects.viewModel.DocumentosViewModel
import kotlinx.coroutines.launch
import kotlin.io.encoding.Base64

@Composable
fun CustomBottomBar(
    navController: NavController,
    clienteRepository: ClienteRepository,
    cadastroClienteViewModel: ClienteViewModel? = null,
    actionsClienteViewModel: ClienteViewModel? = null
) {
    val navBackStackEntry = navController.currentBackStackEntryAsState()
    val rotaAtual = navBackStackEntry.value?.destination?.route
    val coScope = rememberCoroutineScope()

    println("Rota Atual: $rotaAtual")
    println("Nav back stack entry: ${navBackStackEntry}")
    println("nav back stack entry value: ${navBackStackEntry.value}")

    val actualViewModel: ClienteViewModel? = when (rotaAtual) {
        Tela.Consulta.rota, Tela.Detalhes.rota, Tela.Editar.rota, Tela.EditarDocumentos.rota -> actionsClienteViewModel
        Tela.Formulario.rota, Tela.Documentos.rota -> cadastroClienteViewModel
        else -> null
    }

    println("actual view model: $actualViewModel")
    println("cadastro view model: $cadastroClienteViewModel")
    println("actions view model: $actionsClienteViewModel")

    BottomAppBar(
        actions = {
            when (rotaAtual) {
                Tela.Formulario.rota -> {
                    Row(
                        modifier = Modifier.fillMaxWidth()
                            .padding(horizontal = 10.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Button(
                            onClick = {
                                navController.popBackStack()
                            }
                        ) {
                            Text("Cancelar Solicitação")
                        }
                        Button(
                            onClick = {
                                navController.navigate(Tela.Documentos.rota)
                            }
                        ) {
                            Text("Próximo")
                        }
                    }
                }
                Tela.Documentos.rota -> {
                    actualViewModel?.let { vm ->
                        println("rota documentos bottom bar view model: $vm")
                        Row(
                            modifier = Modifier.fillMaxWidth()
                                .padding(horizontal = 10.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Button(
                                onClick = {
                                    navController.popBackStack()
                                }
                            ) {
                                Text("Voltar")
                            }
                            Button(
                                onClick = {
                                    coScope.launch {
                                        println("rota documentos btn view model: $vm")
                                        println("CPF: ${vm.uiState.value}")
                                        vm.salvarCliente()
                                    }
                                }
                            ) {
                                Text("Finalizar")
                            }
                        }
                    }

                }
                Tela.Consulta.rota -> {
                    actualViewModel?.let { vm ->
                        println("rota consulta bottom bar view modeo: $vm")
                        Row(
                            modifier = Modifier.fillMaxWidth()
                                .padding(horizontal = 10.dp),
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Button(
                                onClick = {
                                    coScope.launch {
                                        println("rota consulta btn view model: $vm")
                                        println("CPF: ${vm.uiState.value.cpf}")
                                        vm.buscarClientePorCpf()
                                    }
                                }
                            ) {
                                Text("Consultar")
                            }
                        }
                    }
                }
                Tela.Editar.rota -> {
                    Row(
                        modifier = Modifier.fillMaxWidth()
                            .padding(horizontal = 10.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Button(
                            onClick = {
                                navController.popBackStack()
                            }
                        ) {
                            Text("Cancelar Solicitação")
                        }
                        Button(
                            onClick = {
                                navController.navigate(Tela.EditarDocumentos.rota)
                            }
                        ) {
                            Text("Próximo")
                        }
                    }
                }
                Tela.EditarDocumentos.rota -> {
                    println("rota editardocs rota atual: $rotaAtual")
                    actualViewModel?.let { vm ->
                        println("rota editardocs bottom bar view model: $vm")
                        Row(
                            modifier = Modifier.fillMaxWidth()
                                .padding(horizontal = 10.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Button(
                                onClick = {
                                    navController.popBackStack()
                                }
                            ) {
                                Text("Voltar")
                            }
                            Button(
                                onClick = {
                                    coScope.launch {
                                        println("rota editardocs btn view model: $vm")
                                        println("CPF: ${vm.uiState.value}")
                                        vm.atualizarCliente()
                                    }
                                }
                            ) {
                                Text("Finalizar")
                            }
                        }
                    }
                }
            }
        }
    )
}
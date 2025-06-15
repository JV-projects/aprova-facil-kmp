package edu.fatec.jvprojects

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import edu.fatec.jvprojects.composables.CustomBottomBar
import edu.fatec.jvprojects.composables.WebViewScreen
import edu.fatec.jvprojects.compositionLocal.LocalTokenService
import edu.fatec.jvprojects.compositionLocal.TokenService
import edu.fatec.jvprojects.repository.AdministradorRepository
import edu.fatec.jvprojects.repository.ClienteRepository
import edu.fatec.jvprojects.repository.DocumentosRepository
import edu.fatec.jvprojects.rotas.Tela
import edu.fatec.jvprojects.screens.ConsultaScreen
import edu.fatec.jvprojects.screens.DetalheScreen
import edu.fatec.jvprojects.screens.EditarDocumentosScreen
import edu.fatec.jvprojects.screens.FormularioDocumentos
import edu.fatec.jvprojects.screens.FormularioScreen
import edu.fatec.jvprojects.screens.HomeScreen
import edu.fatec.jvprojects.screens.TesteScreen
import edu.fatec.jvprojects.screens.EditarScreen
import edu.fatec.jvprojects.screens.InternoScreen
import edu.fatec.jvprojects.screens.LoginScreen
import edu.fatec.jvprojects.theme.AppTheme
import edu.fatec.jvprojects.viewModel.ClienteFormEvent
import edu.fatec.jvprojects.viewModel.ClienteViewModel
import edu.fatec.jvprojects.viewModel.DocumentosFormEvent
import edu.fatec.jvprojects.viewModel.DocumentosViewModel
import org.jetbrains.compose.ui.tooling.preview.Preview

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Preview
fun App() {
    val navController = rememberNavController()
    val snackbarState = remember { SnackbarHostState() }
    val navBackStackEntry = navController.currentBackStackEntryAsState()
    val rotaAtual = navBackStackEntry.value?.destination?.route
    val telaAtual = Tela.fromRoute(rotaAtual)
    val clienteRepository = ClienteRepository()
    val documentosRepository = DocumentosRepository()
    val administradorRepository = AdministradorRepository()

    val optionsGraphEntry = remember(navBackStackEntry.value) {
        try {
            navController.getBackStackEntry("options_graph")
        } catch (e: Exception) {
            null
        }
    }
    val optionsViewModel: ClienteViewModel? = optionsGraphEntry?.let {
        viewModel(
            viewModelStoreOwner = it,
            factory = ClienteViewModel.provideFactory(clienteRepository)
        )
    }

    val cadastroGraphEntry = remember(navBackStackEntry.value) {
        try {
            navController.getBackStackEntry("cadastro_cliente_graph")
        } catch (e: Exception) {
            null
        }
    }
    val cadastroViewModel: ClienteViewModel? = cadastroGraphEntry?.let {
        viewModel(
            viewModelStoreOwner = it,
            factory = ClienteViewModel.provideFactory(clienteRepository)
        )
    }

    println("options view model: $optionsViewModel")
    println("cadastro view model: $cadastroViewModel")

    val documentoViewModel: DocumentosViewModel? = if (rotaAtual == Tela.Documentos.rota) {
        viewModel(
            viewModelStoreOwner = checkNotNull(navBackStackEntry.value) {
                "ViewModelStoreOwner nulo para rota $rotaAtual"
            },
            factory = DocumentosViewModel.provideFactory(documentosRepository)
        )
    } else {
        null
    }

    AppTheme {
        Scaffold(
            snackbarHost = { SnackbarHost(hostState = snackbarState) },
            topBar = {
                CenterAlignedTopAppBar(
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer,
                        titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                    ),
                    title = { Text("Aprova Fácil") },
                    navigationIcon = {
                        IconButton(
                            onClick = {
                                if (navController.previousBackStackEntry != null) {
                                    navController.popBackStack()
                                }

                            }
                        ) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = "Voltar"
                            )
                        }
                    }
                )
            },
            content = { paddingValues ->
                val tokenService = remember { TokenService() }
                CompositionLocalProvider(LocalTokenService provides tokenService) {
                    NavHost(
                        modifier = Modifier.padding(paddingValues),
                        navController = navController,
                        startDestination = "home",
                        route = "options_graph"
                    ) {
                        composable(
                            route = "teste/{link}",
                            arguments = listOf(navArgument("link") { type = NavType.StringType })
                        ) { navBackStackEntry ->
                            val link = navBackStackEntry.arguments?.getString("link")
                            val encoded =
                                TesteScreen(link.toString())
                        }
                        // compartilhar view model entre Formulario e Documentos
                        // compartilhar view model entre consulta, detalhes, editar
                        // passar algo para identificar qual opção foi selecionada

                        composable(Tela.Home.rota) { navBackStackEntry ->
                            val parentEntry = remember(navBackStackEntry) {
                                navController.getBackStackEntry("options_graph")
                            }
                            val clienteViewModel: ClienteViewModel = viewModel(
                                viewModelStoreOwner = parentEntry,
                                factory = ClienteViewModel.provideFactory(clienteRepository)
                            )

                            HomeScreen(navController, clienteViewModel)
                        }

                        composable(Tela.Consulta.rota) { navBackStackEntry ->
                            val parentEntry = remember(navBackStackEntry) {
                                navController.getBackStackEntry("options_graph")
                            }
                            val clienteViewModel: ClienteViewModel = viewModel(
                                viewModelStoreOwner = parentEntry,
                                factory = ClienteViewModel.provideFactory(clienteRepository)
                            )

                            LaunchedEffect(clienteViewModel, snackbarState, navController) {
                                clienteViewModel.formEvent.collect { event ->
                                    when (event) {
                                        is ClienteFormEvent.ClienteCarregadoComSucesso -> {

                                            if (event.tela == Tela.Excluir.rota) {
                                                navController.navigate(Tela.Detalhes.rota)
                                            } else {
                                                navController.navigate(event.tela)
                                            }
                                        }

                                        is ClienteFormEvent.ErroAoSalvar -> {
                                            snackbarState.showSnackbar(
                                                message = "Erro: ${event.mensagem}"
                                            )
                                        }

                                        else -> {
                                            snackbarState.showSnackbar(
                                                message = "Erro inesperado: $event"
                                            )
                                        }
                                    }
                                }
                            }

                            ConsultaScreen(navController, snackbarState, clienteViewModel)
                        }
                        composable(Tela.Detalhes.rota) { navBackStackEntry ->
                            val parentEntry = remember(navBackStackEntry) {
                                navController.getBackStackEntry("options_graph")
                            }
                            val clienteViewModel: ClienteViewModel = viewModel(
                                viewModelStoreOwner = parentEntry,
                                factory = ClienteViewModel.provideFactory(clienteRepository)
                            )

                            LaunchedEffect(clienteViewModel, snackbarState, navController) {
                                clienteViewModel.formEvent.collect { event ->
                                    when (event) {
                                        is ClienteFormEvent.DeletadoComSucesso -> {
                                            snackbarState.showSnackbar(
                                                message = "Cliente deletado",
                                                duration = SnackbarDuration.Short
                                            )

                                            navController.navigate(Tela.Home.rota)
                                        }

                                        is ClienteFormEvent.ErroAoSalvar -> {
                                            snackbarState.showSnackbar(
                                                message = "Erro: ${event.mensagem}"
                                            )
                                        }

                                        else -> {
                                            snackbarState.showSnackbar(
                                                message = "Erro inesperado: $event"
                                            )
                                        }
                                    }
                                }
                            }

                            DetalheScreen(navController, snackbarState, clienteViewModel)
                        }
                        composable(Tela.Editar.rota) { navBackStackEntry ->
                            val parentEntry = remember(navBackStackEntry) {
                                navController.getBackStackEntry("options_graph")
                            }
                            val clienteViewModel: ClienteViewModel = viewModel(
                                viewModelStoreOwner = parentEntry,
                                factory = ClienteViewModel.provideFactory(clienteRepository)
                            )

                            EditarScreen(navController, snackbarState, clienteViewModel)
                        }

                        composable(Tela.EditarDocumentos.rota) { navBackStackEntry ->
                            val parentEntry = remember(navBackStackEntry) {
                                navController.getBackStackEntry("options_graph")
                            }
                            val clienteViewModel: ClienteViewModel = viewModel(
                                viewModelStoreOwner = parentEntry,
                                factory = ClienteViewModel.provideFactory(clienteRepository)
                            )

                            LaunchedEffect(clienteViewModel, snackbarState, navController) {
                                clienteViewModel.formEvent.collect { event ->
                                    when (event) {
                                        is ClienteFormEvent.ClienteSalvoComSucesso -> {
                                            snackbarState.showSnackbar(
                                                message = "Cliente com ID: ${event.clienteId} atualizado"
                                            )

                                            navController.navigate(Tela.Home.rota)
                                        }

                                        is ClienteFormEvent.ErroAoSalvar -> {
                                            snackbarState.showSnackbar(
                                                message = "Erro: ${event.mensagem}"
                                            )
                                        }

                                        else -> {
                                            snackbarState.showSnackbar(
                                                message = "Erro inesperado: $event"
                                            )
                                        }
                                    }
                                }
                            }

                            EditarDocumentosScreen(clienteViewModel)
                        }


                        navigation(
                            startDestination = Tela.Formulario.rota,
                            route = "cadastro_cliente_graph"
                        ) {

                            composable(Tela.Formulario.rota) { navBackStackEntry ->


                                val parentEntry = remember(navBackStackEntry) {
                                    navController.getBackStackEntry("cadastro_cliente_graph")
                                }

                                val clienteViewModel: ClienteViewModel = viewModel(
                                    viewModelStoreOwner = parentEntry,
                                    factory = ClienteViewModel.provideFactory(clienteRepository)
                                )

                                FormularioScreen(navController, snackbarState, clienteRepository, clienteViewModel)
                            }
                            composable(Tela.Documentos.rota) { navBackStackEntry ->
                                val parentEntry = remember(navBackStackEntry) {
                                    navController.getBackStackEntry("cadastro_cliente_graph")
                                }

                                val clienteViewModel: ClienteViewModel = viewModel(
                                    viewModelStoreOwner = parentEntry,
                                    factory = ClienteViewModel.provideFactory(clienteRepository)
                                )

                                LaunchedEffect(clienteViewModel, snackbarState, navController) {
                                    clienteViewModel.formEvent.collect { event ->
                                        when (event) {
                                            is ClienteFormEvent.ClienteSalvoComSucesso -> {
                                                snackbarState.showSnackbar(
                                                    message = "Cliente com ID: ${event.clienteId} salvo com sucesso"
                                                )

                                                navController.navigate(Tela.Home.rota)
                                            }

                                            is ClienteFormEvent.ErroAoSalvar -> {
                                                snackbarState.showSnackbar(
                                                    message = "Erro: ${event.mensagem}"
                                                )
                                            }

                                            else -> {
                                                snackbarState.showSnackbar(
                                                    message = "Erro inesperado: $event"
                                                )
                                            }
                                        }
                                    }
                                }

                                FormularioDocumentos(navController, snackbarState, documentosRepository, clienteViewModel)
                            }
                        }

                        composable(Tela.Interno.rota) { InternoScreen(navController) }

                        navigation(
                            startDestination = Tela.Login.rota,
                            route = "adm_graph"
                        ) {
                            composable(Tela.Login.rota) { LoginScreen(administradorRepository, navController, snackbarState) }

                        }


                    }
                }
            },
            bottomBar = {
                if (telaAtual?.showBottomBar == true) {
                    CustomBottomBar(navController, clienteRepository, cadastroViewModel, optionsViewModel)
                }
            }
        )
    }



    LaunchedEffect(documentoViewModel) {
        documentoViewModel?.formEvent?.collect { event ->
            when (event) {
                is DocumentosFormEvent.DocumentosEnviadosComSucesso -> {
                    snackbarState.showSnackbar(
                        message = event.mensagem
                    )
                    if (navController.currentDestination?.route == Tela.Documentos.rota) {
                        navController.navigate(Tela.Home.rota)
                    }
                }
                is DocumentosFormEvent.ErroAoEnviarDocumentos -> {
                    snackbarState.showSnackbar(
                        message = "Erro: ${event.mensagem}"
                    )
                }
            }
        }
    }

}


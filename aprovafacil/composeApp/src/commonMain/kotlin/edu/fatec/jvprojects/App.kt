package edu.fatec.jvprojects

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import edu.fatec.jvprojects.composables.Formulario
import edu.fatec.jvprojects.screens.ConsultaScreen
import edu.fatec.jvprojects.screens.DetalheScreen
import edu.fatec.jvprojects.screens.EditarScreen
import org.jetbrains.compose.ui.tooling.preview.Preview


@Composable
@Preview
fun App() {
    val navController = rememberNavController()

    MaterialTheme {
        NavHost(navController = navController, startDestination = "formulario") {
            composable("formulario") { Formulario(navController) }
            composable("consulta") { ConsultaScreen(navController) }
            composable("detalhes") { DetalheScreen(navController) }
            composable("editar") { EditarScreen(navController) }
        }
    }
}


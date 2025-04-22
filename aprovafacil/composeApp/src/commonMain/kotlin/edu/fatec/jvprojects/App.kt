package edu.fatec.jvprojects

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import edu.fatec.jvprojects.screens.ConsultaScreen
import edu.fatec.jvprojects.screens.DetalheScreen
import org.jetbrains.compose.ui.tooling.preview.Preview


@Composable
@Preview
fun App() {
    val navController = rememberNavController()

    MaterialTheme {
        NavHost(navController = navController, startDestination = "consulta"){
            composable("consulta") { ConsultaScreen(navController) }
            composable("detalhes") { DetalheScreen(navController) }
        }
    }
}


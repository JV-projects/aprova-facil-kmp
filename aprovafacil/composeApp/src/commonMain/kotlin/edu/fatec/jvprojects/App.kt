package edu.fatec.jvprojects

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import edu.fatec.jvprojects.composables.Formulario
import org.jetbrains.compose.ui.tooling.preview.Preview


@Composable
@Preview
fun App() {

    MaterialTheme {
        Formulario()
    }
}


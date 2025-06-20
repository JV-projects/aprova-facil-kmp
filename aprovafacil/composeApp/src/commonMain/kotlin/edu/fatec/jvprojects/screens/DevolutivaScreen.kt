package edu.fatec.jvprojects.screens

import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import edu.fatec.jvprojects.composables.StatusBadge
import edu.fatec.jvprojects.model.dtos.ClienteDevolutivaDTO
import edu.fatec.jvprojects.model.dtos.DevolutivaDTO
import edu.fatec.jvprojects.repository.DevolutivaRepository
import edu.fatec.jvprojects.wrapper.Resultado
import kotlinx.coroutines.launch

@Composable
fun DevolutivaScreen(navController: NavController, snackbar: SnackbarHostState) {

    var repository = DevolutivaRepository()
    var codigoDevoluitva by remember { mutableStateOf("") }
    var devolutiva by remember { mutableStateOf("") }
    var cliente by remember { mutableStateOf<ClienteDevolutivaDTO?>(null) }
    var clienteEncontrado by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()
    val scrollState = rememberScrollState()

    fun enviarCodigoDevolutiva() {
        scope.launch {
            if (codigoDevoluitva.isEmpty()) {
                snackbar.showSnackbar(
                    message = "Preencha o código da devolutiva"
                )
            } else {

                when (val resposta = repository.validarCodigoDevolutiva(codigoDevoluitva)) {
                    is Resultado.Sucesso -> {
                        cliente = resposta.data
                        clienteEncontrado = true
                    }

                    is Resultado.Erro -> {
                        snackbar.showSnackbar(
                            message = resposta.error.mensagem
                        )
                        clienteEncontrado = false
                    }
                }

            }
        }
    }

    fun enviarDevolutiva() {
        scope.launch {
            if (devolutiva.isEmpty()) {
                snackbar.showSnackbar(
                    message = "Preencha a devolutiva"
                )
            } else {

                when (val resposta = repository.registrarDevolutiva(
                    DevolutivaDTO(
                        idCliente = cliente!!.id, devolutiva = devolutiva
                    )
                )) {
                    is Resultado.Sucesso -> {
                        snackbar.showSnackbar(
                            message = resposta.data
                        )
                        navController.popBackStack()
                    }

                    is Resultado.Erro -> {
                        snackbar.showSnackbar(
                            message = resposta.error.mensagem
                        )
                    }
                }

            }
        }
    }

    Surface {
        Box {
            Column(
                modifier = Modifier.padding(10.dp).fillMaxWidth().verticalScroll(scrollState),
                verticalArrangement = Arrangement.spacedBy(10.dp),

            ) {
                Text(text = "Devolutiva", fontSize = 20.sp)
                Text(
                    text = "Insira o código enviando para o seu email para fornecer a devolutiva de um cliente",
                    modifier = Modifier.padding(top = 4.dp)
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    OutlinedTextField(
                        label = { Text("Código") },
                        singleLine = true,
                        value = codigoDevoluitva,
                        onValueChange = { codigoDevoluitva = it },
                        modifier = Modifier.weight(1f, fill = true)
                    )

                    Button(
                        modifier = Modifier.wrapContentWidth(),
                        onClick = { enviarCodigoDevolutiva() }
                    ) {
                        Text("Validar")
                    }

                }

                if (clienteEncontrado && cliente != null) {

                    Text(
                        text = "Fornecendo devolutiva para: ",
                        modifier = Modifier.padding(top = 10.dp, bottom = 10.dp),
                        fontSize = 16.sp
                    )
                    CardCliente(
                        title = cliente!!.nome,
                        subtitle = cliente!!.email,
                        status = cliente!!.status
                    )
                    OutlinedTextField(
                        value = devolutiva,
                        onValueChange = { devolutiva = it },
                        modifier = Modifier.fillMaxWidth(),
                        minLines = 8,
                        maxLines = 10,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
                    )
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End
                    ) {
                        Button(onClick = { enviarDevolutiva() }) {
                            Text("Salvar")
                        }
                    }
                }
            }

        }
    }
}

@Composable
fun CardCliente(
    title: String, subtitle: String, status: String
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .border(
                width = 1.dp,
                color = Color.Gray,
                shape = RoundedCornerShape(4.dp)
            )
            .padding(12.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(text = title, fontSize = 16.sp)
                Text(text = subtitle, fontSize = 14.sp, fontWeight = FontWeight.SemiBold)
            }
            StatusBadge(
                status
            )
        }
    }
}
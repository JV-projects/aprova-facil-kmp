package edu.fatec.jvprojects.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import edu.fatec.jvprojects.model.enums.StatusCliente

@Composable
fun StatusBadge(statusCliente: String) {
    val status = StatusCliente.fromString(statusCliente)

    val backgroundColor: Color
    val contentColor: Color = Color.Black
    val displayText: String

    when (status) {
        StatusCliente.PENDENTE -> {
            backgroundColor = Color(0xFFFFB74D)
            displayText = status.textoExibicao
        }
        StatusCliente.AGUARDANDO_ANALISE -> {
            backgroundColor = Color(0xFFFFD54F)
            displayText = status.textoExibicao
        }
        StatusCliente.PENDENTE_ATENDIMENTO -> {
            backgroundColor = Color(0xFF9FA8DA)
            displayText = status.textoExibicao
        }
        StatusCliente.EM_ATENDIMENTO -> {
            backgroundColor = Color(0xFFFFF176)
            displayText = status.textoExibicao
        }
        StatusCliente.ATENDIMENTO_CONCLUIDO -> {
            backgroundColor = Color(0xFFA5D6A7)
            displayText = status.textoExibicao
        }

        else -> {
            backgroundColor = Color.LightGray
            displayText = "Desconhecido"
        }
    }

    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(4.dp))
            .background(backgroundColor)
            .padding(horizontal = 8.dp, vertical = 6.dp)
    ) {
        Text(
            text = displayText,
            color = contentColor,
            fontSize = 14.sp
        )
    }
}
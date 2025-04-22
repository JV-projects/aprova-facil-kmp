package edu.fatec.jvprojects.composables

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp

@Composable
fun TextWithLabel(label: String, informacao: String = "-") {
    Column {
        Text(
            label,
            color = Color.Gray,
            fontSize = 14.sp
        )
        Text(
            informacao,
            color = Color.DarkGray,
            fontSize = 18.sp
        )
    }
}